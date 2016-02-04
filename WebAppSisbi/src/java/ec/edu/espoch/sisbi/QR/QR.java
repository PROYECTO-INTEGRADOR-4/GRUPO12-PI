/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.QR;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author foqc
 */
public class QR {

    private FileOutputStream fileOut;//para exportar un archivo
    private ByteArrayOutputStream out;//para exportar un array binario de un archivo
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    public StreamedContent generarQR(String codigo) throws Exception {
        StreamedContent image = null;
        if (codigo.length() > 0) {
            String texto = codigo;
            //recibe el stream del codigo qr generado por la lbreria qrcode
            out = QRCode.from(texto).withSize(WIDTH, HEIGHT).to(ImageType.PNG).stream();
            try {
                fileOut = new FileOutputStream(new File("temporal.png"));
                fileOut.write(out.toByteArray());
                fileOut.flush();
                fileOut.close();
                //mostrando imagen generada
                BufferedImage canvas = ImageIO.read(new File("temporal.png"));
                BufferedImage bufferedImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = bufferedImg.createGraphics();
                g2.drawImage(canvas, null, null);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferedImg, "png", os);
                image = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png", "miQR");
            } catch (Exception e) {
                throw e;
            }
        }
        return image;
    }

    public StreamedContent generarQR1(String codigo) throws Exception {
        StreamedContent image = null;
        if (codigo.length() > 0) {
            String texto = codigo;
            //recibe el stream del codigo qr generado por la lbreria qrcode
            out = QRCode.from(texto).withSize(WIDTH, HEIGHT).to(ImageType.PNG).stream();
            try {

                BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = bufferedImg.createGraphics();
                g2.drawString(codigo, 0, 10);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferedImg, "png", os);
                image = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
            } catch (Exception e) {
                throw e;
            }
        }
        return image;
    }
}
