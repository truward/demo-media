package com.truward.demo.media;

import com.truward.demo.media.generator.ImageGenerator;
import com.truward.demo.media.generator.TextGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Shabanov
 */
@ParametersAreNonnullByDefault
public class DemoMediaServlet extends HttpServlet {
  private final TextGenerator textGenerator = new TextGenerator();
  private final ImageGenerator imageGenerator = new ImageGenerator();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (textGenerator.generateText(req, resp)) {
      return;
    }

    if (imageGenerator.generateImage(req, resp)) {
      return;
    }

    super.doGet(req, resp);
  }
}
