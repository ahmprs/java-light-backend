import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Main extends HttpServlet {

  
  // define a method for handling any `GET` request
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    this.handelRequest(req, res);
  }

  
  // define a method for handling any `POST` request
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    this.handelRequest(req, res);
  }

  
  // whether the request is a `POST` or a `GET`
  // we handle them alike.
  void handelRequest(HttpServletRequest req, HttpServletResponse res) {
    
    // sets the response type to `json`
    // (javascript object notation)
    res.setContentType("application/json");

    
    // In order to make a separation between `front-end` design and
    // business logic in the `back-end`, unlike many codes out there I suggest not to
    // pollute your server-side code with `markups` and obvoisly `javascripts`.
    // sending back response in the form of `json` can pave the path.
    /* -------------------------------------------------- */


    // an `API` function to be called
    // in this servlet
    String fn = null;
    

    // a sample parameter
    String x = null;

    // another parameter
    String y = null;

    // a printter stream object
    // used to write the response into.
    PrintWriter pw = null;


    try {

      // provides the responce stream
      pw = res.getWriter();

      // try to extract the parameters 
      // from either `POST` or `GET` requests
      fn = req.getParameter("fn");
      x = req.getParameter("x");
      y = req.getParameter("y");

      // based on which `API` function is 
      // called the proper response is provided
      if (fn.equalsIgnoreCase("add")) {
        try {
          int a = Integer.parseInt(x);
          int b = Integer.parseInt(y);
          int c = add(a, b);
          pw.println(this.success("" + c));
        } catch (Exception ex) {
          pw.println(this.failure(ex.getMessage()));
        }
      } else if (fn.equalsIgnoreCase("mul")) {
        try {
          int a = Integer.parseInt(x);
          int b = Integer.parseInt(y);
          int c = mul(a, b);
          pw.println(this.success("" + c));
        } catch (Exception ex) {
          pw.println(this.failure(ex.getMessage()));
        }
      }

    } catch (Exception ex) {
      if (pw != null)
        pw.println(this.failure(ex.getMessage()));
    }
    if (pw != null)
      // close the reponse stream.
      pw.close();
  }

  // a method for coating a given
  // string by a double quotation
  private String coat(String s) {
    return "\"" + s + "\"";
  }

  // a sample server-side `API` which is 
  // called from client-side script throug a 
  // `POST` or `GET` request. It adds
  // two iteger arguments.
  private int add(int a, int b) {
    return a + b;
  }


  // another server-side `API`. It multiplies
  // two iteger arguments.
  private int mul(int a, int b) {
    return a * b;
  }

  // provides a `json` string object
  // and wraps the result in it.
  // It is used in the case of `success`.
  private String success(String res) {
    String r = "";
    r += coat("success");
    r += ":";
    r += coat("true");
    r += ",";
    r += coat("resp");
    r += ":";
    r += coat(res);
    r = "{" + r + "}";
    return r;
  }

  // provides a `json` string object
  // and wraps the error in it.
  // It is used in the case of `exception`.
  private String failure(String err) {
    String r = "";
    r += coat("success");
    r += ":";
    r += coat("false");
    r += ",";
    r += coat("err");
    r += ":";
    r += coat(err);
    r = "{" + r + "}";
    return r;
  }
}
/* ================================================== */ 