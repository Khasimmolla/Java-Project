package emailapplication;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.text.SimpleDateFormat;
public class Email 
{
	
	private String FirstName;
	private String LastName;
	private String Password;
	private int defaultPasswordLength=10;
	private String email;
	private String Department;
	private String companySuffix="mollacompamny.com";
	private int MailBoxCapacity=500;
	private String AlternateEmail;
	private String ChangePassword;
	private String EmployeeID;
	private static final String EMPLOYEE_FILE = "employees.csv";
	//Constructor for getting the first name and last
	//if no constructor is called, java has default constructor
	//Constructor is used to Intialize Java Objects, it does not have a return type, Name should be same as class Name
	public Email(String FirstName, String LastName)
	{
		this.FirstName= FirstName;
		this.LastName= LastName;
		//System.out.println("Email is created "+FirstName+" "+LastName);
		
		this.Department=setDepartment(); // Calling the method that will take the department from user and return that value
		//System.out.println("Your Department is: "+Department);
		
		this.Password=RandomPassword(defaultPasswordLength);
		System.out.println("Your Default Random Password is: "+Password);
		
		
		//combing everything to create the email
		email=FirstName.toLowerCase()+"."+LastName.toLowerCase()+"@"+Department+"."+companySuffix;
		//System.out.println("Your Email is: "+email);

		this.EmployeeID=GenerateEmployeeID();
		
		saveEmployeeDetails();
	}
	
	// Get the department
	private String setDepartment()
	{
		System.out.println("New Employee: "+FirstName+"\nDepartment Codes:\n 1. Sales\n 2.Development\n 3. Accounting\n 4. Support\n 0. None\nEnter Your Department Code ");
		Scanner s= new Scanner(System.in);
		int choice=s.nextInt();
		s.nextLine();
		
		switch (choice) {
        case 1: return "Sales";
        case 2: return "Development";
        case 3: return "Accounting";
        default: return "General";
    }
		
					
		
	}
	private String GenerateEmployeeID()
	{
		int lastID=getLastEmployeeID();
		int newID=lastID+1;
		
		String yearMonth=new SimpleDateFormat("yyyyMM").format(new Date());
		
		String newEmployeeID=String.format("EMP%s%03d", yearMonth, newID);
		
		saveLastEmployeeID(newID);
		
		return newEmployeeID;
	}
	//reading the last Employee ID from the first row of the CSV file
	private int getLastEmployeeID()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE)))
		{
            String firstLine = reader.readLine();
            if (firstLine != null && firstLine.startsWith("LastID,"))
            {
                return Integer.parseInt(firstLine.split(",")[1]);
            }
		} catch (IOException | NumberFormatException e)
		{
			return 0;
		}
		return 0;
	}
	
	//Saving the Last Employee ID in the first row
	private void saveLastEmployeeID(int lastId)
	{
		try {
            File file = new File(EMPLOYEE_FILE);
            boolean fileExists = file.exists();
            StringBuilder content = new StringBuilder();
            if (fileExists) 
            {
                try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE)))
                {
                    reader.readLine();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        content.append(line).append("\n");
                    }
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE)))
            {
                writer.write("LastID," + lastId + "\n");
                writer.write(content.toString());
            }
        } catch (IOException e)
		{
            System.out.println("Error saving employee ID!");
        }
    }

	
	//Generate random password
	private String RandomPassword(int length)
	{
		String PasswordSet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()1234567890";
		char[] Password =new char[length];
		for(int i=0;i<length;i++)
		{
			int random=(int)(Math.random() * PasswordSet.length());
			Password[i]=PasswordSet.charAt(random);
		}
		return new String(Password);
	}
	
	//Saving the Employee details to CSV file
	private void saveEmployeeDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE, true)))
        {
            
            File file = new File(EMPLOYEE_FILE);
            if (file.length() == 0) 
            {
                writer.write("LastID,0\n"); 
                writer.write("Employee ID,First Name,Last Name,Department,Email\n");
            }
            
            writer.write(String.format("%s,%s,%s,%s,%s\n", EmployeeID, FirstName, LastName, Department, email));
        } catch (IOException e) 
        {
            System.out.println("Error saving employee details!");
        }
    }
	
	//Set the mailCapacity
	public void setMailBoxCapacity(int Capacity)
	{
		this.MailBoxCapacity=Capacity;
	}
	
	//Set the Alternate Email
	public void setAlternateEmail(Scanner s)
	{
		while (true) {
	        System.out.print("Enter an alternate email (or press Enter to skip): ");
	        String input = s.nextLine().trim();

	        if (input.isEmpty())
	        {
	            this.AlternateEmail = "None";
	            break;
	        } 
	        else if (isValidEmail(input))
	        {
	            this.AlternateEmail = input;
	            break;
	        } 
	        else 
	        {
	            System.out.println("Invalid email format. Please enter a valid email.");
	        }
	    }
	}
	private boolean isValidEmail(String email) {
	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    return Pattern.matches(emailRegex, email);
	}
	

	//Change the Password
	public void ChangePassword(String Password)
	{
		this.ChangePassword=Password;
	}
	
	public int getMailBoxCapacity()
	{
		return MailBoxCapacity;
	}
	public String getAlternateEmail()
	{
		return AlternateEmail;
	}
	public String getPassword()
	{
		return Password;
	}
	 public void changePassword() {
	        Scanner sc = new Scanner(System.in);
	        System.out.print("Enter your new password: ");
	        this.Password = sc.nextLine();
	        System.out.println("Password changed successfully!");
	    }
	public void ShowInfo()
	{
		System.out.println("\n===== Employee Information =====");
        System.out.println("Employee ID: " + EmployeeID);
        System.out.println("Name: " + FirstName + " " + LastName);
        System.out.println("Company Email: " + email);
        System.out.println("Mailbox Capacity: " + MailBoxCapacity + " MB");
        System.out.println("================================");
	}

}
