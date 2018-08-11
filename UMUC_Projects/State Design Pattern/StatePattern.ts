class Phone {
    // References to the possible Phone States
    private ringAndVibrateState: PhoneState = new RingAndVibrateState(this);
    private ringState: PhoneState = new RingState(this);
    private vibrateState: PhoneState = new VibrateState(this);
    private silentState: PhoneState = new SilentState(this);
    private airplaneState: PhoneState = new AirplaneState(this);

    private mode: PhoneState; // current mode of the phone's notification
    notification: string = null; // the notification message
    
    setMode(_mode: PhoneState){
        this.mode = _mode
    }
    getMode(){
        return this.mode;
    }
    constructor(){
        this.mode = this.ringAndVibrateState; // default for new phones
    }

    pushNotification(message){
        this.mode.pushNotification(message);
    }

    getNotification(){
        return this.notification? this.notification.toString(): "";
    }

    // Getters for the Phone's States
    getRingVibrateState(){
        return this.ringAndVibrateState;
    }
    getRingState(){
        return this.ringState;
    }
    getVibrateState(){
        return this.ringState;
    }
    getSilentState(){
        return this.silentState;
    }
    getAirplaneState(){
        return this.airplaneState;
    }
}

/** 
 * Interface of PhoneState that contains state based functions.
*/
export interface PhoneState {
    pushNotification(message: string): void; //
}

class RingAndVibrateState implements PhoneState{
    phoneDevice: Phone; // context of the phone

    constructor(newPhone){
        //Passing in the current phone's context
        this.phoneDevice = newPhone;
    }
    pushNotification(message){
        // Setting the phone's notification to the passed in message
        this.phoneDevice.notification = message;
        console.log("{{Ring}}", "{{Vibrate}}", this.phoneDevice.notification);

    }
}

class RingState implements PhoneState{
    phoneDevice: Phone; // context of the phone

    constructor(newPhone){
        //Passing in the current phone's context
        this.phoneDevice = newPhone;
    }
    pushNotification(message){
        // Setting the phone's notification to the passed in message
        this.phoneDevice.notification = message;
        console.log("{{Ring}}", this.phoneDevice.notification);
    }
}

class VibrateState implements PhoneState{
    phoneDevice: Phone; // context of the phone

    constructor(newPhone){
        //Passing in the current phone's context
        this.phoneDevice = newPhone;
    }
    pushNotification(message){
        // Setting the phone's notification to the passed in message
        this.phoneDevice.notification = message;
        console.log("{{Vibrate}}", this.phoneDevice.notification);

    }
}

class SilentState implements PhoneState{
    phoneDevice: Phone; // context of the phone

    constructor(newPhone){
        //Passing in the current phone's context
        this.phoneDevice = newPhone;
    }
    pushNotification(message){
        // Setting the phone's notification to the passed in message
        this.phoneDevice.notification = message;
        console.log(this.phoneDevice.notification);

    }
}

class AirplaneState implements PhoneState{
    phoneDevice: Phone; // context of the phone

    constructor(newPhone){
        //Passing in the current phone's context
        this.phoneDevice = newPhone;
    }
    pushNotification(message){
        this.phoneDevice.notification = null;
        return; //Can't get a notification in airplane mode
    }
}


function main() {
    const myPhone = new Phone();
    console.log(myPhone.getMode().constructor.name);
    console.log("Checking Notification... ", myPhone.getNotification()? myPhone.getNotification(): "No Notifications");
    myPhone.pushNotification("Hello");
    console.log("Checking Notification... ", myPhone.getNotification()? myPhone.getNotification(): "No Notifications");
    console.log();


    myPhone.setMode(myPhone.getSilentState());
    console.log(myPhone.getMode().constructor.name);
    myPhone.pushNotification("Hello Again");
    console.log("Checking Notification... ", myPhone.getNotification()? myPhone.getNotification(): "No Notifications");
    console.log();

    myPhone.setMode(myPhone.getVibrateState());
    console.log(myPhone.getMode().constructor.name);
    myPhone.pushNotification("What are you up to?");
    console.log("Checking Notification... ", myPhone.getNotification()? myPhone.getNotification(): "No Notifications");
    console.log();

    myPhone.setMode(myPhone.getAirplaneState());
    console.log(myPhone.getMode().constructor.name);
    myPhone.pushNotification("Hello Are you there?");
    console.log("Checking Notification... ", myPhone.getNotification()? myPhone.getNotification(): "No Notifications");
    console.log();
}
main();
