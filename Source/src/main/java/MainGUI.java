import Configuration.Config;
import Controllers.CustomersController;
import Controllers.OrdersController;
import Controllers.PartsController;
import Controllers.PresetsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class handles all of the GUI aspects of the program.
 */
public class MainGUI {
    //Private members
    private Config _config = new Config();
    private DecimalFormat _df = new DecimalFormat("#####.00");
    private final int SPACING = 25;
    private boolean _loggedin;
    private Stage _stage;
    private PartsController _partsController;
    private OrdersController _ordersController;
    private CustomersController _customersController;
    private PresetsController _presetsController;

    //values for keeping track of the user
    private int _currentCustomerId;
    private int _currentOrderId;


    public MainGUI() {
        _loggedin = false;
        _partsController = new PartsController();
        _ordersController = new OrdersController();
        _customersController = new CustomersController();
        _presetsController = new PresetsController();
        _currentOrderId = -99;
    }

    /**
     * Assigns the stage within the MainGUI class.
     * @param stage
     */
    public void SetMainGuiStage(Stage stage) {
        _stage = stage;
    }

    /**
     * Will change the scene within the MainGUI class.
     * @param scene
     */
    private void updateScene(Scene scene) {
        this._stage.setScene(scene);
    }

    /**
     * Executes the login screen.
     */
    public void buildGUI() {
        Scene scene = buildLogin();
        _stage.setScene(scene);
    }

    /**
     * This method will build the login screen and return the resulting Scene value.
     * @return Scene
     */
    private Scene buildLogin() {
        Label title = new Label("BestPrice");
        Label title2 = new Label("Please login to order your computer parts\nor review your previous orders.");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label errorLabel = new Label(" ");
        errorLabel.setId("errorLabel");

        Image logo = new Image("bestPriceLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(100);
        logoView.setFitWidth(100);

        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String pw = passwordTF.getText();
            String un = usernameTF.getText();
            if(_customersController.login(un, pw)) {
                _currentCustomerId = _customersController.getCurrentCustomerId();
                buildDashboard();
            }
        });
        Button signUpButton = new Button("Create An Account");

        signUpButton.setOnAction(event ->{
            buildAccount();
        });

        //display
        title.setStyle("-fx-font-size: 25pt");
        HBox titleH = new HBox(SPACING,logoView,title);
        titleH.setAlignment(Pos.CENTER);
        VBox titleV = new VBox(titleH,title2);
        VBox usernameV = new VBox(usernameLabel,usernameTF);
        VBox passwordV = new VBox(passwordLabel,passwordTF);
        HBox buttonH = new HBox(SPACING,loginButton,signUpButton);
        buttonH.setAlignment(Pos.CENTER_RIGHT);
        VBox mainContainer = new VBox(SPACING, titleV, errorLabel, usernameV, passwordV, buttonH);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

        Scene scene =  new Scene(mainContainer);
        scene.getStylesheets().add("login.css");
        return scene;
    }

    /**
     * This window allows the user to input their information to create an account.
     */
    public void buildAccount(){
        //Labels
        Label title = new Label("Creating An Account");
        Label infoLabel = new Label("Please fill out the fields below: ");
        Label contactInfo = new Label("Contact Information");
        Label firstNameLabel = new Label("First Name: ");
        Label lastNameLabel = new Label("Last Name: ");
        Label phoneLabel = new Label("Phone Number: ");
        Label emailLabel = new Label("Email: ");
        Label address = new Label("Address ");
        Label streetLabel = new Label("Street: ");
        Label cityLabel = new Label("City: ");
        Label stateLabel = new Label("State: ");
        Label zipCodeLabel = new Label("Zip code: ");
        Label loginInfo = new Label("Login Information");
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        Label passwordInfo = new Label("* Password must contain at least 8 characters, " +
                "1 number, and 1 special character.");
        Label confirmPasswordLabel = new Label("Confirm Password: ");
        Label confirmMsg = new Label(" ");

        //change the style of the titles
        title.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold");
        contactInfo.setId("title");
        address.setId("title");
        loginInfo.setId("title");

        //textFields
        TextField firstNameTF = new TextField();
        TextField lastNameTF = new TextField();
        TextField phoneTF = new TextField();
        TextField emailTF = new TextField();
        TextField streetTF = new TextField();
        TextField cityTF = new TextField();
        TextField zipCodeTF = new TextField();
        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();
        PasswordField confirmTF = new PasswordField();

        //set textFields' size
        emailTF.setPrefSize(300,10);
        streetTF.setPrefSize(250,10);

        //create the state options
        ArrayList<String> state = new ArrayList<String>();
        String[] s = {"Alabama" ,"Alaska", "Arizona","Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
                "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota","Mississippi","Missouri",
                "Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina",
                "North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia","Washington","West Virginia","Wisconsin","Wyoming"};
        for(int i = 0;i<s.length;i++){
            state.add(s[i]);
        }
        ObservableList stateOL = FXCollections.observableArrayList(state);
        ComboBox stateCB = new ComboBox(stateOL);

        //create an image
        Image logo = new Image("bestPriceLogo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(80);
        logoView.setFitWidth(80);

        //create a button for finishing creating an account
        Button backToLogin = new Button("Back to login");
        backToLogin.setOnAction(event -> {
            updateScene(buildLogin());
        });

        Button creatA = new Button("Create Account");
        //set on action
        creatA.setOnAction(event ->{
            try {
                System.out.println("Creating Account...");
                String firstName = firstNameTF.getText();
                String lastName = lastNameTF.getText();
                String phoneNumber = phoneTF.getText();
                String email = emailTF.getText();
                String street = streetTF.getText();
                String city = cityTF.getText();
                String stateStr = stateCB.getSelectionModel().getSelectedItem().toString();
                String zipCode = zipCodeTF.getText();
                String newUsername = usernameTF.getText();
                String newPassword = passwordTF.getText();

                confirmMsg.setText(" ");
                passwordInfo.setId(" ");
                if(newPassword.length()<8){
                    passwordInfo.setId("errorLabel");
                }
                else{
                    boolean specialChar = false;
                    boolean number = false;

                    for(int j=0;j < newPassword.length();j++){
                        char c = newPassword.charAt(j);

                        if(Character.isDigit(c)){
                            number = true;
                        };

                        if(!Character.isLetter(c)){
                            specialChar = true;
                        }
                    }

                    if(!specialChar||!number){
                        passwordInfo.setId("errorLabel");
                    }
                }
                int zip;
                if(!newPassword.equals(confirmTF.getText())){
                    confirmMsg.setText("Passwords do not match.");
                    confirmMsg.setId("errorLabel");
                }
                try {
                    zip = Integer.parseInt(zipCode);
                } catch (Exception e) {
                    System.out.println("Cannot parse zip code");
                    zip = 12345;
                }

                //make sure that everything went smoothly with the registration
                if(!_customersController.register
                        (firstName, lastName, phoneNumber, email, street, city, stateStr, zip
                                , newUsername, newPassword)) {
                    confirmMsg.setText("Error During Registration");
                    confirmMsg.setId("errorLabel");
                } else {
                    System.out.println("Successfully Created User");
                    JOptionPane.showMessageDialog(null,"Account Created");
                    updateScene(buildLogin());
                }
            }
            catch (NullPointerException n)
            {
                JOptionPane.showMessageDialog(null,"Please fill out the entire form.");
            }
        });

        //node formatting
        HBox row1 = new HBox(SPACING+10,logoView,title);
        row1.setAlignment(Pos.CENTER_LEFT);
        HBox fnameRow = new HBox(firstNameLabel, firstNameTF);
        HBox lnameRow = new HBox(lastNameLabel,lastNameTF);
        HBox nameRow = new HBox(SPACING+10,fnameRow,lnameRow);
        HBox emailRow = new HBox(emailLabel,emailTF);
        HBox phoneRow = new HBox(phoneLabel,phoneTF);
        HBox streetRow = new HBox(streetLabel,streetTF);
        HBox cityRow = new HBox(cityLabel,cityTF);
        HBox addressRow = new HBox(SPACING+10,streetRow,cityRow);
        HBox zipRow = new HBox(zipCodeLabel,zipCodeTF);
        HBox stateRow = new HBox(stateLabel,stateCB);
        HBox addressRow2 = new HBox(SPACING+10,stateRow,zipRow);
        HBox usernameRow = new HBox(usernameLabel,usernameTF);
        HBox passwordRow = new HBox(passwordLabel,passwordTF);
        HBox confirmRow = new HBox(confirmPasswordLabel,confirmTF);

        VBox main = new VBox(SPACING,row1,infoLabel,contactInfo,nameRow,phoneRow,emailRow,address,addressRow,addressRow2,
                loginInfo,usernameRow,passwordRow,passwordInfo,confirmRow,confirmMsg,creatA, backToLogin);
        main.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

        Scene account = new Scene(main);
        account.getStylesheets().add("login.css");
        updateScene(account);
    }

    /**
     * This window displays a simple dashboard from which the user can select 4 activities:
     * Order Admin
     * Parts Admin
     * Create an Order
     * Your Orders
     */
    private void buildDashboard() {
        Label title = new Label("Ordering System Dashboard");
        title.setId("title");

        /*
            create the buttons to handle which view is loaded
         */
        Button ordersButton = new Button("Order Admin");
        ordersButton.setOnAction(event -> {
            buildAdminOrders();
        });
        Button partsButton = new Button("Parts Admin");
        partsButton.setOnAction(event -> {
            buildAdminParts();
        });

        Button ordersCreatorButton = new Button("Create an Order");
        ordersCreatorButton.setOnAction(event -> {
            buildOrderCreator();
        });

        Button viewOrdersButton = new Button("Your Orders");
        viewOrdersButton.setOnAction(event -> {
            buildCurrentCustomerOrders();
        });

        HBox navButtonsContainer = new HBox(SPACING, ordersButton, partsButton, ordersCreatorButton, viewOrdersButton);
        VBox mainContainer = new VBox(SPACING, title, navButtonsContainer);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        scene.getStylesheets().add("style.css");
        updateScene(scene);
    }

    /**
     * This method will find any unfinished orders the user has and display them.
     */
    private void buildCurrentCustomerOrders() {
        Label title = new Label("Current Open Orders");
        title.setId("title");
        //only used if the user wants to edit the order

        //orders gui components
        ListView<String> orderPartsListView = new ListView<>();
        ListView<String> currentOrdersListView = new ListView<>();

        //get the details for all unpaid customer orders
        HashMap<Integer, ArrayList<ArrayList>> orders = _ordersController.getCustomerOrders(_currentCustomerId);
        //get all the id's for the orders
        Set<Integer> orderIds = orders.keySet();
        for(Integer i : orderIds) {
            currentOrdersListView.getItems().add(i.toString());
        }

        //when the user clicks an order, update the listview to show all parts associated with that order
        currentOrdersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //clear the order parts listview
                orderPartsListView.getItems().clear();
                //get the parts associated with that order
                int orderId = Integer.parseInt(newValue.toString());
                //set the current orderId to the global variable if the user wants to edit the order
                _currentOrderId = orderId;
                System.out.println("GETTING PARTS FOR ORDERID: " + orderId + " AND CUSTOMER NUM: " + _currentCustomerId);
                ArrayList<String> orderParts = _ordersController.getOrderParts(orderId, _currentCustomerId);
                orderPartsListView.getItems().addAll(orderParts);
            }
        });
        HBox orderComponentsContainer = new HBox(SPACING, currentOrdersListView, orderPartsListView);

        //nav controls
        Button backToDash = new Button("Back to Dashboard");
        backToDash.setOnAction(event -> {
            buildDashboard();
        });
        HBox navControlContainer = new HBox(SPACING, backToDash);

        Button editThisOrder = new Button("Edit this order");
        editThisOrder.setOnAction(event -> {
            if(_currentOrderId > 0) {
                buildOrderCreator();
            }
        });

        VBox mainContainer = new VBox(SPACING, title, orderComponentsContainer, navControlContainer);
        Scene scene = new Scene(mainContainer);
        scene.getStylesheets().add("style.css");
        updateScene(scene);
    }

    /**
     * This method shows the interface the user will use to set parts and presets to add to their order.
     */
    private void buildOrderCreator() {
        ComboBox partTypes = new ComboBox();

        ListView<String> partsListView = new ListView<>();
        ListView<String> selectedPartsListView = new ListView<>();

        //if the user has not selected an existing order, check if they have any open orders.
        if(_currentOrderId < 0) {
            HashMap<Integer, ArrayList<ArrayList>> customerOrders =
                    _ordersController.getCustomerOrders(_currentCustomerId);
            //if there are no unpaid orders, create a new order
            if(customerOrders.isEmpty()) {
                _ordersController.createCustomerOrder(_currentCustomerId);
                _currentOrderId = _ordersController.getEmptyCustomerOrderId(_currentCustomerId);
            } else {
                _currentOrderId = Integer.parseInt(customerOrders.keySet().toArray()[0].toString());
            }
        }
        Label totalNum = new Label();

        //check to see if the customer has an order they would like to edit, if not, create a new order
        if (_currentOrderId < 0) {
            //since the order id is -99 still, create an order for the customer
            _ordersController.createCustomerOrder(_currentCustomerId);
            //get the id of the new order (it will be empty)
            _currentOrderId = _ordersController.getEmptyCustomerOrderId(_currentCustomerId);
        }

        //init the orderParts list with existing values (if any)
        selectedPartsListView
                .getItems()
                .addAll(_ordersController.getOrderParts(_currentOrderId, _currentCustomerId));


        //Order Interface Labels
        Label title = new Label("Customize Your Order");
        title.setId("title");
        Label partLabel = new Label("Choose your parts");
        Label or = new Label("OR");
        Label view = new Label("View: ");
        Label cart = new Label("Shopping Cart");
        Label subTotalLabel = new Label("Subtotal: ");
        Label totalLabel = new Label("Total (+6% tax): ");
        totalLabel.setPrefWidth(300);

        //Order Interface Buttons
        Button viewPresetsButton = new Button("View Presets");
        Button addButton = new Button("Add \u2192");
        addButton.setOnAction(event -> {
            Object[] parts = partsListView.getSelectionModel().getSelectedItems().toArray();
            for (Object p : parts) {
                int partId = Integer.parseInt(p.toString().split(":")[0]);
                _ordersController.addPartToCustomerOrder(_currentOrderId, partId, 1);
            }
            selectedPartsListView.getItems().clear();
            selectedPartsListView
                    .getItems()
                    .addAll(_ordersController.getOrderParts(_currentOrderId, _currentCustomerId));
        });
        Button removeButton = new Button("Remove \u2190");
        removeButton.setOnAction(event -> {
            String currentPart = selectedPartsListView.getSelectionModel().getSelectedItems().get(0);
            int partId = Integer.parseInt(currentPart.split(":")[0]);
            _ordersController.removePartFromCustomerOrder(_currentOrderId, partId);
            selectedPartsListView.getItems().clear();
            selectedPartsListView
                    .getItems()
                    .addAll(_ordersController.getOrderParts(_currentOrderId, _currentCustomerId));
        });
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> {
            _ordersController.clearOrderParts(_currentOrderId);
            selectedPartsListView.getItems().clear();
        });
        Button checkPriceButton = new Button("Check Price");
        checkPriceButton.setOnAction(event -> {
            double subtotal = _ordersController.getOrderTotal(_currentOrderId);
            subTotalLabel.setText("Subtotal: $" + subtotal);
            double total = subtotal * (1 +_config.TAXRATE);
            total = Double.parseDouble(_df.format(total));
            totalLabel.setText("Total (+6% tax): $" + total);
        });
        Button submitOrderButton = new Button("Submit Order");
        submitOrderButton.setOnAction(event -> {
            int btn = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "Confirm Order:", "Order", btn);
            System.out.println(result);
            if(result == JOptionPane.YES_OPTION) {
                _ordersController.submitOrder(_currentOrderId);
                buildDashboard();
                _currentOrderId = -99;
                JOptionPane.showMessageDialog(null, "Order Purchased!");
            }
        });

        Button backToDashButton = new Button("Back to Dashboard");
        backToDashButton.setOnAction(event -> {
            buildDashboard();
        });

        //Order Interface formatting
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);


        partTypes.getItems().addAll("Graphics Card", "Keyboard", "Monitor", "Motherboard",
                "Peripherals", "Storage");
        partTypes.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                partsListView.getItems().clear();
                ArrayList<String> parts = _partsController.getPartsByCategory(newValue.toString());
                partsListView.getItems().addAll(parts);
            }
        });

        HBox partOptions = new HBox(view,partTypes);
        partOptions.setSpacing(5);

        GridPane optionGrid = new GridPane();
        optionGrid.add(partLabel,0,0);
        optionGrid.add(or,1,0);
        optionGrid.add(viewPresetsButton,2,0);
        optionGrid.add(partOptions,0,1);
        optionGrid.add(cart,2,1);

        or.setPadding(new Insets(0,50,0,0));

        optionGrid.setAlignment(Pos.CENTER);
        optionGrid.setHgap(150);
        optionGrid.setVgap(10);
        optionGrid.setPadding(new Insets(5));

        VBox buttonBox = new VBox(addButton,removeButton,clearButton, checkPriceButton);

        HBox partSelectionBox = new HBox(partsListView,buttonBox,selectedPartsListView);

        partSelectionBox.setAlignment(Pos.CENTER);
        partSelectionBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(30);

        HBox subTotalBox = new HBox(subTotalLabel);
        HBox totalBox = new HBox(totalLabel, totalNum, backToDashButton);
        HBox navButtonBox = new HBox(SPACING, submitOrderButton, backToDashButton);

        GridPane orderResults = new GridPane();
        orderResults.add(subTotalBox,0,0);
        orderResults.add(totalBox,0,1);
        orderResults.add(navButtonBox,1,1);

        subTotalBox.setSpacing(SPACING);
        totalBox.setSpacing(SPACING);

        orderResults.setAlignment(Pos.CENTER);
        orderResults.setHgap(330);
        orderResults.setVgap(10);
        orderResults.setPadding(new Insets(5));

        //Scene buttons
        viewPresetsButton.setOnAction(event -> {
            buildOrderPresets();
        });

        //Set scene and show interface
        VBox masterBox = new VBox(titleBox,optionGrid,partSelectionBox,orderResults);
        Scene scene = new Scene(masterBox);
        scene.getStylesheets().add("style.css");
        updateScene(scene);
    }

    /**
     * This method will display the interface used to select presets for a user's order.
     */
    private void buildOrderPresets() {
        //Preset Interface Labels
        Label presetTitle = new Label("Presets");
        presetTitle.setId("title");

        //Preset Interface formatting
        HBox presetTitleBox = new HBox(presetTitle);
        presetTitleBox.setAlignment(Pos.CENTER);
        presetTitleBox.setPadding(new Insets(SPACING));

        //listview for the list of presets
        ListView<String> presetListView = new ListView<>();
        //listview for the list of parts in each preset
        ListView<String> presetPartsListView = new ListView<>();
        presetListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int presetId = Integer.parseInt(newValue.split(":")[0]);
                presetPartsListView.getItems().clear();
                ArrayList<String> partNames = _presetsController.getPresetParts(presetId);
                presetPartsListView.getItems().addAll(partNames);
            }
        });
        //get preset names
        ArrayList<String> presetNames = new ArrayList<>();
        ArrayList<HashMap<String, String>> presets = _presetsController.getPresets();
        for (HashMap h : presets) {
            String presetId = h.get("PRESET_ID").toString();
            String presetName = h.get("PRESET_NAME").toString();
            presetNames.add(presetId+ ": " + presetName);
        }
        presetListView.getItems().addAll(presetNames);

        //create the listview to display the list of parts in each preset

        HBox presetsContainer = new HBox(SPACING, presetListView, presetPartsListView);

        Button addAllToCart = new Button("Add All to Cart");
        addAllToCart.setOnAction(event -> {
            Object[] partsList = presetPartsListView.getItems().toArray();
            for(Object o : partsList) {
                //get the part id from the part string
                try {
                    int partId = Integer.parseInt(o.toString().split(":")[0]);
                    _ordersController.addPartToCustomerOrder(_currentOrderId, partId, 1);
                    presetPartsListView.getItems().clear();
                } catch (Exception e) {
                    System.out.println("Cannot add part: " + e);
                }

            }
        });

        Button removeFromCart = new Button("Remove From Cart");
        Button clearCart = new Button("Clear");
        Button backToOrderPage = new Button("Back to Order Page");
        backToOrderPage.setOnAction(event -> {
            buildOrderCreator();
        });

        HBox presetButtonBox = new HBox(removeFromCart,clearCart, addAllToCart);
        presetButtonBox.setAlignment(Pos.CENTER);
        presetButtonBox.setSpacing(20);

        VBox backBox = new VBox(backToOrderPage);
        backBox.setAlignment(Pos.CENTER_RIGHT);
        VBox masterPresetBox = new VBox(SPACING,presetTitleBox,presetsContainer, presetButtonBox,backBox);
        masterPresetBox.setPadding(new Insets(SPACING,SPACING,SPACING, SPACING));
        Scene presetScene = new Scene(masterPresetBox);
        presetScene.getStylesheets().add("style.css");
        updateScene(presetScene);
    }

    /**
     * This window displays previous orders for the user to examine.
     */
    public void buildAdminOrders() {
        Label title = new Label("DB Admin");
        title.setId("title");
        /*
            create the gui components for displaying the order data
         */
        Label orderPartsListViewLabel = new Label("Order Details");
        ListView<String> orderPartsListView = new ListView<>();
        VBox orderPartsContainer = new VBox(SPACING, orderPartsListViewLabel, orderPartsListView);

        /*
            create the gui components for displaying the id for each order
         */
        Label orderListViewLabel = new Label("Orders");
        //this will display all open customer orders
        ListView<String> orderListView = new ListView<>();
        //this will get the hashmap of all cusomer orders
        HashMap<Integer, ArrayList<ArrayList>> orderQuery = _ordersController.getAllCustomerOrders();
        System.out.println(orderQuery);
        //get the order id's for the orderlistview
        Set<Integer> orderIds = orderQuery.keySet();
        //create a list of orderId's when clicked they will display the order info
        for(Integer i : orderIds) {
            orderListView.getItems().addAll("Order Number: " + i.toString());
        }
        VBox orderListViewContainer = new VBox(SPACING, orderListViewLabel, orderListView);
        //when a user clicks on an order, load the data into the partslistview
        orderListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //get the id for the selected order
                Integer selectedOrderId = Integer.parseInt(newValue.replace("Order Number: ", ""));
                //get the parts for the selected order
                ArrayList<ArrayList> newParts = orderQuery.get(selectedOrderId);
                //add all the details to the order parts list so that users can view them
                //clear the existing parts from the list
                orderPartsListView.getItems().clear();
                for(ArrayList a: newParts) {
                    String part = a.toString().replace("[", "").replace("]", "");
                    orderPartsListView.getItems().addAll(part);
                }
            }
        });


        /*
            compose the gui components and update the scene.
        */

        //create a button to navigate back to dashboard
        Button dashButton = new Button("Back to Dashboard");
        dashButton.setOnAction(event -> {
            buildDashboard();
        });
        HBox ordersMainContainer = new HBox(SPACING, orderListViewContainer, orderPartsContainer);
        VBox mainContainer = new VBox(SPACING, title, ordersMainContainer, dashButton);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        scene.getStylesheets().add("style.css");
        updateScene(scene);
    }

    /**
     * This window will show all existing parts in a ListView.
     */
    private void buildAdminParts() {
        Label title = new Label("Parts");
        title.setId("title");
        /*
            Create the gui component that will display details for a given part
         */
        Label partsDetailLabel = new Label("Part Details");
        ListView<String> partsDetailListView = new ListView<>();

        /*
            Create the gui that will display the list of parts
            when selected it will update the partsDetail list view
         */
        Label partsLabel = new Label("Parts List");
        ListView<String> partsListView = new ListView<>();
        ArrayList<String> partNames = _partsController.getPartNames();
        partsListView.getItems().addAll(partNames);
        VBox partsListViewContainer = new VBox(partsLabel, partsListView);
        HBox partsMainContainer = new HBox(SPACING, partsListViewContainer);

        //create a button to navigate back to dashboard
        Button dashButton = new Button("Back to Dashboard");
        dashButton.setOnAction(event -> {
            buildDashboard();
        });
        VBox mainContainer = new VBox(SPACING, title, partsMainContainer, dashButton);
        mainContainer.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        Scene scene = new Scene(mainContainer);
        scene.getStylesheets().add("style.css");
        updateScene(scene);
    }

}
