import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageEncryption {

 
    public static void processImage(String inputPath, String outputPath, int key, boolean isEncryption) {
        try {
           
            File inputFile = new File(inputPath);
            BufferedImage image = ImageIO.read(inputFile);

            int width = image.getWidth();
            int height = image.getHeight();

            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);

                    
                    int alpha = (pixel >> 24) & 0xFF;
                    int red = (pixel >> 16) & 0xFF;
                    int green = (pixel >> 8) & 0xFF;
                    int blue = pixel & 0xFF;

                    
                    red = red ^ key;
                    green = green ^ key;
                    blue = blue ^ key;

                    
                    if (isEncryption) {
                        int temp = red;
                        red = blue;
                        blue = temp;
                    } else {
                        int temp = blue;
                        blue = red;
                        red = temp;
                    }

                   
                    int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    image.setRGB(x, y, newPixel);
                }
            }

            
            File outputFile = new File(outputPath);
            ImageIO.write(image, "png", outputFile);
            System.out.println((isEncryption ? "Encryption" : "Decryption") + " completed. Output saved to: " + outputPath);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.println("Enter the input image file path:");
        String inputPath = scanner.nextLine();

       
        System.out.println("Enter the output image file path:");
        String outputPath = scanner.nextLine();

        
        System.out.println("Enter the encryption/decryption key (integer value):");
        int key = scanner.nextInt();

        
        System.out.println("Choose an operation: 1 for Encryption, 2 for Decryption:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            processImage(inputPath, outputPath, key, true);
        } else if (choice == 2) {
            processImage(inputPath, outputPath, key, false);
        } else {
            System.out.println("Invalid choice. Exiting.");
        }

        scanner.close();
    }
}
