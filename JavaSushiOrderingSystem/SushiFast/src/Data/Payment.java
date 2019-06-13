package Data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
    

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CC_LENGTH = 16;
    private int paymentID;
    private int orderID;
    private double orderTotal;
    private String paymentType;
    private String cardNumber;
    private String expiryDate;
    private String returnCode;
    private String cardCVV;
    private boolean statusFlag;
    
    public Payment(int orderID, String paymentType, String cardNumber, String expiryDate, String cardCVV, double orderTotal) {
        this.paymentID = 0;
        this.orderID = orderID;
        this.orderTotal = orderTotal;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardCVV = cardCVV;
        this.returnCode = null;
        this.setStatusFlag(false);
    }

    public Payment(int paymentID, int orderID, String paymentType, String returnCode, double orderTotal, boolean statusFlag) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.orderTotal = orderTotal;
        this.paymentType = paymentType;
        this.returnCode = returnCode;
        this.statusFlag = statusFlag;
    }
    
    public void processPayment(){
        
    	//Get current month/year
    	SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterYear = new SimpleDateFormat("YY");
	    Date month = new Date();
        Date year = new Date();
        
        if (this.expiryDate.matches("^(0[1-9]|1[0-2])/?([0-9]{2})$")) {
        	String[] expiryValues = this.expiryDate.split("/");
		    int currentMonth = Integer.parseInt(formatterMonth.format(month));	
		    int currentYear = Integer.parseInt(formatterYear.format(year));
		    int cardMonth = Integer.parseInt(expiryValues[0]);
		    int cardYear = Integer.parseInt(expiryValues[1]);	        
		    
		    //Check to see if card is still valid
		    if (cardYear >= currentYear) {
		    	if ((cardYear == currentYear) && (cardMonth <= currentMonth)) this.setStatusFlag(false);
		    	else this.setStatusFlag(true);
		    }
		    else this.setStatusFlag(false);
        } else this.setStatusFlag(false); 
	    
        // This is where you would add in code to process the payment though a bank or other service
	    // Instead we are simply demoing this capability and generating a random transaction confirmation code
	    // Will reject card number 1111 1111 1111 1111
	    this.cardNumber = this.cardNumber.replace(" ", ""); //remove spaces if they exist
        if (this.cardNumber.equals("1111111111111111")) this.setStatusFlag(false);  
        //Disallow Credit Cards Numbers that are not 16 digits in length
        if (this.cardNumber.length() != CC_LENGTH) this.setStatusFlag(false);
        if (!(this.cardNumber.matches("[0-9]+"))) this.setStatusFlag(false);
        if (!(this.cardCVV.length() == 3 && this.cardCVV.matches("[0-9]+"))) this.setStatusFlag(false);
        
        
        //Simulates generating a transaction code by a payment system
        int count = 8;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
        	int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
        	builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        this.returnCode = builder.toString();
        
        //Store the transaction information in the database. Not storing any card info for security reasons.
        DatabaseConnector.storePayment(this.paymentID, this.orderID, this.orderTotal, this.paymentType, this.returnCode, this.statusFlag); 
        
    }

	public boolean getStatusFlag() {
        return statusFlag;
	}

	public void setStatusFlag(boolean statusFlag) {
        this.statusFlag = statusFlag;
	}
	
	public String getReturnCode() {
        return this.returnCode;
	}

    public int getOrderID() {
        return this.orderID;
    }

    public int getPaymentID() {
        return this.paymentID;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public double getOrderTotal() {
        return this.orderTotal;
    }


}
