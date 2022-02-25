package com.increff.employee.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.controller.OrderItemApiController;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.model.OrderItemForms;
import com.increff.employee.model.OrderXmlForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional
    public void delete(Integer id) throws ApiException {
        OrderPojo orderPojo = dao.select(id);
        if (orderPojo.getComplete() == 1) {
            throw new ApiException("Cannot delete completed order");
        }
        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemService.delete(orderItemPojo.getId());
        }
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, OrderPojo p) throws ApiException {
        OrderPojo ex = getCheck(id);
        // ex.setTime(p.getTime());
        ex.setComplete(p.getComplete());
        ex.setCost(p.getCost());
        dao.update(ex);
    }

    @Transactional
    public OrderPojo getCheck(Integer id) throws ApiException {
        OrderPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return p;
    }

    public List<OrderPojo> getByTime(long time, long time2) throws ApiException {
        List<OrderPojo> p = dao.selectByTime(time, time2);
        if (p == null) {
            throw new ApiException("No orders within constrained dates");
        }
        return p;
    }

    @Transactional
    public void setComplete(int id) throws ApiException {
        OrderPojo p = get(id);
        if (orderItemService.getByOrderId(p.getId()).size() == 0) {
            throw new ApiException("There are no order items!");
        }
        p.setComplete(1);
        update(id, p);
    }

    // @Transactional
    public String generateXML(Integer id)
            throws ApiException, JAXBException, FileNotFoundException {
        OrderPojo p = get(id);

        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        OrderItemForms orderItemForms = new OrderItemForms();
        orderItemForms.setOrderItemFormData(orderItemService.convert(orderItemPojos));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        Date date = Calendar.getInstance().getTime();

        OrderXmlForm orderXmlForm = new OrderXmlForm();
        orderXmlForm.setId(p.getId());
        orderXmlForm.setTotal(String.format("%.2f", p.getCost()));
        orderXmlForm.setItems(orderItemService.convert(orderItemPojos));
        orderXmlForm.setDate(dateFormat.format(date));

        JAXBContext context = JAXBContext.newInstance(OrderXmlForm.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        String fname = "./xml_data/order_" + id.toString() + ".xml";
        File file = new File(fname);
        jaxbMarshaller.marshal(orderXmlForm, file);

        return file.getAbsolutePath();
    }

    public void generatePdf(String fname) throws ApiException {
        try {
            File xmlfile = new File(fname);
            File xsltfile = new File(
                    "/home/abhk943/Documents/increff/employee-spring-full2/src/main/java/com/increff/employee/xls_model/OrderPdfModel.xsl");
            File pdfDir = new File(fname.substring(0, fname.lastIndexOf('/')) + "/generated_pdf");
            pdfDir.mkdirs();
            fname = fname.substring(fname.lastIndexOf('/') + 1);
            fname = fname.substring(0, fname.lastIndexOf("."));
            File pdfFile = new File(pdfDir, fname + ".pdf");
            // System.out.println(pdfFile.getAbsolutePath());
            // configure fopFactory as desired
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // configure foUserAgent as desired
            // Setup output
            OutputStream out = new FileOutputStream(pdfFile);
            out = new java.io.BufferedOutputStream(out);
            try {
                // Construct fop with desired output format
                Fop fop;
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
                // Setup input for XSLT transformation
                Source src = new StreamSource(xmlfile);
                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());
                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);
            } catch (FOPException | TransformerException e) {
                throw new ApiException("Error generating pdf!");
                // TODO Auto-generated catch block
                // e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (IOException exp) {
            // exp.printStackTrace();
            throw new ApiException("Error writing to file!");
        }
    }

}
