import files.payload;
import io.restassured.path.json.JsonPath;
/*"1. Print No of courses returned by API\n" +
        "\n" +
        "2.Print Purchase Amount\n" +
        "\n" +
        "3. Print Title of the first course\n" +
        "\n" +
        "4. Print All course titles and their respective Prices\n" +
        "\n" +
        "5. Print no of copies sold by RPA Course\n" +
        "\n" +
        "6. Verify if Sum of all Course prices matches with Purchase Amount";*/

public class complexjsonprblems {
    public static void main(String[] args) {

        JsonPath js = new JsonPath(payload.complexjs());
        // 1.Print No of courses returned by API
       int size= js.getInt("courses.size()");
       System.out.println(size);
       //2.Print Purchase Amount
      int amount=  js.getInt("dashboard.purchaseAmount");
     // System.out.println(amount);

      //3 . Print Title of the first course\n"
       String firsttitle= js.getString("courses[0].title");
       //System.out.println(firsttitle);

        //"4. Print All course titles and their respective Prices"
        for(int i=0;i<size;i++)
        {
           String titles= js.getString("courses["+i+"].title");
           int prices=js.getInt("courses["+i+"].price");
           System.out.println(titles+":"+prices+" ");
        }

        //"5. Print no of copies sold by RPA Course\n" +
        for(int i=0;i<size;i++)
        {
            String gettitle=js.getString("courses["+i+"].title");
            if(gettitle.equals("RPA"))
            {
               int copiessold= js.getInt("courses["+i+"].copies");
               System.out.println(copiessold+" by given book");
               break;
            }
        }
        //Verify if Sum of all Course prices matches with Purchase Amount



    }
}
