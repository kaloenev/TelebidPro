<%@ page import="java.awt.Color" %>
<%@ page import="java.awt.Font" %>
<%@ page import="java.awt.Graphics2D" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.util.Random" %>
<%
  String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  StringBuilder sb = new StringBuilder();
  Random random = new Random();
  for (int i = 0; i < 6; i++) {
    char c = chars.charAt(random.nextInt(chars.length()));
    sb.append(c);
  }
  String captcha = sb.toString();

  // Store captcha in session
  session.setAttribute("captcha", captcha);

  // Output captcha image
  int width = 100;
  int height = 40;
  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  Graphics2D g2d = image.createGraphics();
  g2d.setColor(Color.WHITE);
  g2d.fillRect(0, 0, width, height);
  // Draw text
  g2d.setColor(Color.BLACK);
  g2d.setFont(new Font("Arial", Font.BOLD, 20));
  g2d.drawString(captcha, 10, 25);
  // Draw lines
  g2d.setColor(Color.GRAY);
  for (int i = 0; i < 5; i++) {
    int x1 = random.nextInt(width);
    int y1 = random.nextInt(height);
    int x2 = random.nextInt(width);
    int y2 = random.nextInt(height);
    g2d.drawLine(x1, y1, x2, y2);
  }
  g2d.dispose();

  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  ImageIO.write(image, "jpeg", baos);
  byte[] imageData = baos.toByteArray();

  response.setContentType("image/jpeg");
  response.getOutputStream().write(imageData);
  response.getOutputStream().close();
%>