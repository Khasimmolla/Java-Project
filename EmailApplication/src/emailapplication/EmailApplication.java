package emailapplication;
import java.util.*;
public class EmailApplication 
{

	public static void main(String[] args) 
	{
	
		Scanner S=new Scanner(System.in);
		System.out.print("Enter first name: ");
        String firstName = S.nextLine();

        System.out.print("Enter last name: ");
        String lastName = S.nextLine();
        
        Email em = new Email(firstName, lastName);
        
        em.setAlternateEmail(S);
		em.ShowInfo();
		 System.out.println("\nDo you want to change your password? (yes/no): ");
	        String response = S.nextLine();

	        if (response.equalsIgnoreCase("yes"))
	        {
	            em.changePassword();
	        }

	        System.out.println("Thank you! Your account is set up.");
	        S.close();

	}

}
