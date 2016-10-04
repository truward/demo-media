package com.truward.demo.media.generator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Alexander Shabanov
 */
@ParametersAreNonnullByDefault
public final class ImageGenerator {

  public static final String SAMPLE_COVER = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 30 40\">\n" +
      "  <rect fill=\"#3465a4\" width=\"30\" height=\"32\"/>\n" +
      "  <rect fill=\"#8ae234\" width=\"30\" height=\"8\" x=\"0\" y=\"32\"/>\n" +
      "  <text x=\"2\" y=\"10\" font-size=\"10\" fill=\"#fff\"><![CDATA[Demo]]></text> \n" +
      "</svg>";

  public boolean generateImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String pathInfo = request.getPathInfo();
    if (!"/image".equals(pathInfo)) {
      return false;
    }

    // TODO: check Accept header

    final PrintWriter writer = response.getWriter();
    writer.write(SAMPLE_COVER);
    response.setContentType("image/svg+xml");
    return true;
  }
}
