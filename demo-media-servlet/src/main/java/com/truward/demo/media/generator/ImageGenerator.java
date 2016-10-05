package com.truward.demo.media.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String DEFAULT_BG_COLOR = "#aaa";
  private static final String DEFAULT_FG_COLOR = "#fff";
  private static final int DEFAULT_WIDTH = 64;
  private static final int DEFAULT_HEIGHT = 64;
  private static final int DEFAULT_FONT_SIZE = 12;

  //private static final String

  private static final String SAMPLE_COVER = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 30 40\">\n" +
      "  <rect fill=\"#3465a4\" width=\"30\" height=\"32\"/>\n" +
      "  <rect fill=\"#8ae234\" width=\"30\" height=\"8\" x=\"0\" y=\"32\"/>\n" +
      "  <text x=\"2\" y=\"10\" font-size=\"10\" fill=\"#fff\"><![CDATA[Demo]]></text>\n" +
      "</svg>";

  public boolean tryGenerateImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // TODO: check Accept header

    if (tryMapSimpleImage(request, response)) {
      return true;
    }

    log.trace("Skipping {}", request.getPathInfo());
    return false;
  }

  //
  // Private
  //

  private boolean tryMapSimpleImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (!"/image".equals(request.getPathInfo())) {
      return false;
    }

    final PrintWriter w = response.getWriter();
    if ("cover".equals(request.getParameter("type"))) {
      w.write(SAMPLE_COVER);
      setSvgContentTypeAndCacheControl(response);
      return true;
    }

    final int width = getIntQueryParam(request, "width", DEFAULT_WIDTH);
    final int height = getIntQueryParam(request, "height", DEFAULT_HEIGHT);
    final String title = getQueryParam(request, "title", String.format("%dx%d", width, height));
    final String bgColor = getQueryParam(request, "bg-color", DEFAULT_BG_COLOR);
    final String fgColor = getQueryParam(request, "fg-color", DEFAULT_FG_COLOR);
    final int fontSize = getIntQueryParam(request, "font-size", DEFAULT_FONT_SIZE);

    w.write("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 ");
    w.write(Integer.toString(width));
    w.write(' ');
    w.write(Integer.toString(height));
    w.write("\">\n");

    w.write("<rect fill=\"");
    w.write(bgColor);
    w.write("\" width=\"");
    w.write(Integer.toString(width));
    w.write("\" height=\"");
    w.write(Integer.toString(height));
    w.write("\"/>\n");

    w.write("<text x=\"");
    w.write(Integer.toString(width / 2));
    w.write("\" y=\"");
    w.write(Integer.toString(height / 2));
    w.write("\" font-size=\"");
    w.write(Integer.toString(fontSize));
    w.write("\" fill=\"");
    w.write(fgColor);
    w.write("\" text-anchor=\"middle\" alignment-baseline=\"central\"><![CDATA[");
    w.write(title);
    w.write("]]></text>\n");

    w.write("</svg>\n");
    setSvgContentTypeAndCacheControl(response);

    return true;
  }

  private static String getQueryParam(HttpServletRequest request, String name, String defaultValue) {
    final String val = request.getParameter(name);
    if (val == null || val.isEmpty()) {
      return defaultValue;
    }

    return val;
  }

  private static int getIntQueryParam(HttpServletRequest request, String name, int defaultValue) {
    final String val = request.getParameter(name);
    if (val == null || val.isEmpty()) {
      return defaultValue;
    }

    return Integer.parseInt(val);
  }

  private static void setSvgContentTypeAndCacheControl(HttpServletResponse response) {
    response.setContentType("image/svg+xml");
    response.setHeader("Cache-Control", "max-age=3600, public");
  }
}
