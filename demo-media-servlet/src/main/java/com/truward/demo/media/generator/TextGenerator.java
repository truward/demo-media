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
public final class TextGenerator {
  private static final String DEMO_TEXT = "Lorem ipsum dolorem sit amet";

  public boolean tryGenerateText(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String pathInfo = request.getPathInfo();
    if (!"/text".equals(pathInfo)) {
      return false;
    }

    // TODO: check Accept header

    final PrintWriter writer = response.getWriter();
    writer.write(DEMO_TEXT);
    response.setContentType("text/plain");
    return true;
  }
}
