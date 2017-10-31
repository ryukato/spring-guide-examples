package app.blur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ForkJoinPool;

public class Application {
    public static void main(String[] args) throws Exception {
        String srcName = "image/pexels-photo-296282.jpeg";
        File srcImageFile = getResourceFile(srcName);
        BufferedImage image = ImageIO.read(srcImageFile);
        System.out.println("Source image: " + srcName);

        BufferedImage blurredImage = blur(image);

        String destName = srcImageFile.getParent() + "/" + "blurred-pexels-photo-296282.png";
        File destFile = new File(destName);
        ImageIO.write(blurredImage, "PNG", destFile);

        System.out.println("Output image: " + destName);

    }


    private static InputStream getResoruce(String fileName) {
        return Application.class.getClassLoader().getResourceAsStream(fileName);
    }
    private static File getResourceFile(String fileName) throws Exception {
        return new File(Application.class.getClassLoader().getResource(fileName).toURI());
    }

    static BufferedImage blur(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];

        System.out.println("Array size is " + src.length);

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(Integer.toString(processors) + " processor"
                + (processors != 1 ? "s are " : " is ")
                + "available");

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
        ForkJoinPool pool = new ForkJoinPool(processors);

        System.out.println("ForkJoinPool info: " + pool);
        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();

        System.out.println("Image blur took " + (endTime - startTime) +
                " milliseconds.");

        BufferedImage dstImage =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);

        return dstImage;
    }


}
