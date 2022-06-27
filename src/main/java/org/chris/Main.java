package org.chris;

import org.chris.contoller.VendingMachineController;
import org.chris.dao.ItemDao;
import org.chris.dao.ItemDaoFileImpl;
import org.chris.dao.VendingMachineAuditDao;
import org.chris.dao.VendingMachineAuditDaoFileImpl;
import org.chris.service.VendingMachineServiceLayer;
import org.chris.service.VendingMachineServiceLayerImpl;
import org.chris.ui.UserIO;
import org.chris.ui.UserIOConsoleImpl;
import org.chris.ui.VendingMachineView;

public class Main {
    public static void main(String[] args)
    {
        UserIO myIo = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        ItemDao myDao = new ItemDaoFileImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        VendingMachineController controller = new VendingMachineController(service, myView);
        controller.run();
    }
}