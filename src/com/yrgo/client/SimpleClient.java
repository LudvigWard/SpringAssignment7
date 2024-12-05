
package com.yrgo.client;

import java.util.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.calls.CallHandlingService;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

public class SimpleClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = null;
        try {
            container = new ClassPathXmlApplicationContext("production-application.xml");

            CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
            CallHandlingService callService = container.getBean(CallHandlingService.class);
            DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

            customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));

            Call newCall = new Call("Larry Wall called from Acme Corp");
            Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2016, Calendar.JANUARY, 1), "rac");
            Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2016, Calendar.JANUARY, 1), "rac");

            List<Action> actions = new ArrayList<>();
            actions.add(action1);
            actions.add(action2);

            try {
                callService.recordCall("CS03939", newCall, actions);
            } catch (CustomerNotFoundException e) {
                System.out.println("That customer doesn't exist");
            }

            Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
            System.out.println("Here are the outstanding actions:");
            for (Action next : incompleteActions) {
                System.out.println(next);
            }
        } finally {
            if (container != null) {
                container.close();
            }
        }
    }
}