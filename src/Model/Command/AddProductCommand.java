package Model.Command;

import Model.MyException;

public class AddProductCommand implements ExecuteCommand {
    private final StoreCommand storeCommand;
    private final String name;
    private final String serialNum;
    private final int storePrice;
    private final int customerPrice;
    private final String customerName;
    private final String phoneNumber;
    private final boolean subscribedStatus;

    public AddProductCommand(
            StoreCommand storeCommand,
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) {
        this.storeCommand = storeCommand;
        this.name = name;
        this.serialNum = serialNum;
        this.storePrice = storePrice;
        this.customerPrice = customerPrice;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.subscribedStatus = subscribedStatus;
    }

    @Override
    public void execute() throws MyException {
        storeCommand.addProduct(
                name,
                serialNum,
                storePrice,
                customerPrice,
                customerName,
                phoneNumber,
                subscribedStatus
        );
    }
}
