import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class sumvalidation {

    @Test
    public void  sumofallcourses()
    {
        JsonPath js = new JsonPath(payload.complexjs());
        // 1.Print No of courses returned by API
        int size= js.getInt("courses.size()");
        System.out.println(size);
        int purchaseamt=js.getInt("dashboard.purchaseAmount");
        int sum=0;

        for(int i=0;i<size;i++)
        {
            String titles= js.getString("courses["+i+"].title");
            int prices=js.getInt("courses["+i+"].price");
            int cop=js.getInt("courses["+i+"].copies");
            sum+=prices*cop;
        }
        if(sum==purchaseamt)
        {
            System.out.print("amount is matching");
        }
        else
        {
            System.out.print("amount is not matching");
        }
    }

}
