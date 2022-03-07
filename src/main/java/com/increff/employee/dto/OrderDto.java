package com.increff.employee.dto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.FileOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import com.increff.employee.dto.helper.OrderDtoHelper;
import com.increff.employee.dto.helper.OrderItemDtoHelper;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemXmlForm;
import com.increff.employee.model.OrderXmlForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderDto
 */
@Service
public class OrderDto {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    public Integer get() throws ApiException {
        OrderPojo p = new OrderPojo();
        orderService.add(p);
        return p.getId();
    }

    public void delete(int id) throws ApiException {
        orderService.delete(id);
    }

    public OrderForm get(int id) throws ApiException {
        OrderPojo p = orderService.get(id);
        return OrderDtoHelper.convert(p);
    }

    public List<OrderForm> getAll() {
        List<OrderPojo> list = orderService.getAll();
        List<OrderForm> list2 = new ArrayList<OrderForm>();
        for (OrderPojo p : list) {
            list2.add(OrderDtoHelper.convert(p));
        }
        return list2;
    }

    public void storeOrder(Integer id) throws ApiException, JAXBException, IOException {
        orderService.setComplete(id);
        OrderXmlForm orderXmlForm = getXmlForm(id);
        String fname = generateXML(id, orderXmlForm);
        List<String> xsl_dir_pdf_paths = generatePaths(fname);
        generatePdf(fname, xsl_dir_pdf_paths);
    }

    private List<String> generatePaths(String fname) {
        List<String> strings = new ArrayList<String>();
        strings.add(
                "/home/abhk943/Documents/increff/employee-spring-full2/src/main/java/com/increff/employee/xls_model/OrderPdfModel.xsl");
        strings.add(fname.substring(0, fname.lastIndexOf('/')) + "/generated_pdf");
        fname = fname.substring(fname.lastIndexOf('/') + 1);
        fname = fname.substring(0, fname.lastIndexOf(".")) + ".pdf";
        strings.add(fname);
        return strings;
    }

    public void generatePdf(String fname, List<String> paths) throws ApiException, IOException {
        OutputStream out = null;
        try {
            File xmlfile = new File(fname);
            File xsltfile = new File(paths.get(0));
            File pdfDir = new File(paths.get(1));
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, paths.get(2));
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            out = new FileOutputStream(pdfFile);
            out = new java.io.BufferedOutputStream(out);

            Fop fop;
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
            Source src = new StreamSource(xmlfile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
            // } catch (FOPException | TransformerException e) {
            // throw new ApiException("Error generating pdf!");
            // } finally {
            // out.close();
            // }
        } catch (IOException | FOPException | TransformerException exp) {
            throw new ApiException("Error generating pdf!");
        } finally {
            out.close();
        }
    }

    public String generateXML(Integer id, OrderXmlForm orderXmlForm)
            throws ApiException, JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(OrderXmlForm.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        String fname = "./xml_data/order_" + id.toString() + ".xml";
        File file = new File(fname);
        jaxbMarshaller.marshal(orderXmlForm, file);
        return file.getAbsolutePath();
    }

    public OrderXmlForm getXmlForm(Integer id) throws ApiException {
        OrderPojo p = orderService.get(id);
        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        List<OrderItemXmlForm> orderItemXmlForms = convert(orderItemPojos);
        return OrderItemDtoHelper.convert(p, orderItemXmlForms);
    }

    private List<OrderItemXmlForm> convert(List<OrderItemPojo> orderItemPojos) throws ApiException {
        List<OrderItemXmlForm> orderItemXmlForms = new ArrayList<OrderItemXmlForm>();
        for (OrderItemPojo p : orderItemPojos) {
            Double mrp = productService.getByBarcode(p.getProductBarcode()).getMrp();
            String name = productService.getByBarcode(p.getProductBarcode()).getName();
            orderItemXmlForms.add(OrderItemDtoHelper.convert(p, mrp, name));
        }
        return orderItemXmlForms;
    }

    public byte[] getPdf(Integer id) {
        String filepath =
                "/home/abhk943/Documents/increff/employee-spring-full2/xml_data/generated_pdf/order_"
                        + id.toString() + ".pdf";
        try {
            byte[] inFileBytes = Files.readAllBytes(Paths.get(filepath));
            byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(inFileBytes);
            return encoded;
        } catch (IOException e) {
        }
        return null;
    }

    public void update(int id, OrderForm f) throws ApiException {
        OrderPojo p = OrderDtoHelper.convert(f);
        orderService.update(id, p);
    }

}
