import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SimpleUsage {
  public static void main(String[] args) throws Exception {
    try (OutputStream os = new FileOutputStream("src/main/resources/autorization.pdf");
         OutputStream osTest = new FileOutputStream("src/main/resources/test.pdf")) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.useFastMode();
      builder.withFile(new File("src/main/resources/autorization.html"));
      builder.toStream(os);
      builder.run();

      CleanerProperties props = new CleanerProperties();

      // set some properties to non-default values
      props.setTranslateSpecialEntities(true);
      props.setTransResCharsToNCR(true);
      props.setOmitComments(true);

      // do parsing
      TagNode tagNode = new HtmlCleaner(props).clean(
        new File("src/main/resources/test.html")
      );

      // serialize to xml file
      new PrettyXmlSerializer(props).writeToFile(
        tagNode, "src/main/resources/test_good.html", "utf-8"
      );

      File file = new File("src/main/resources/test_good.html");

      PdfRendererBuilder builderTest = new PdfRendererBuilder();
      builderTest.useFastMode();
      builderTest.withFile(file);
      builderTest.toStream(osTest);
      builderTest.run();

    }
  }
}