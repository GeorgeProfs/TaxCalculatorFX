import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongoApp {
  private MongoClient mongoClient;

  MongoApp() {
    System.out.println("MongoApp constructed");
    try {
      mongoClient = new MongoClient("localhost", 27017);
    } catch (UnknownHostException unknownHostException) {
      System.out.println("Connection problem");
    }
  }

  public boolean addPayResultToMongo(int result) {
    DB database = mongoClient.getDB("TaxCalculator");
    DBCollection dbCollection = database.getCollection("PayResult");

    BasicDBObject basicDBObject = new BasicDBObject("_id", "123")
      .append("type", result);

    dbCollection.insert(basicDBObject);
    return true;
  }

}
