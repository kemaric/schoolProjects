class Phone {
    private mode: PhoneState; // current mode of the phone's notification
    private notification: string = null; // the notification message
    setMode(_mode: PhoneState){
        this.mode = _mode
    }
    getMode(){
        return this.mode.toString();
    }
    constructor(){
        this.mode = PhoneState.RING_AND_VIBRATE; // default for new phones
    }
    getNotification(){
        return this.notification? this.notification.toString(): "";
    }
   /**
    * The Push Notification Action
    **/ 
    pushNotification(message: string) {
        if(this.mode == PhoneState.RING) {
            // Do Notification action in ring mode
            console.log("{{Ring}}", message);
        }else if(this.mode == PhoneState.VIBRATE) {
            // Do Notification action in vibrate
            console.log("{{Vibrate}}", message);
        }else if(this.mode == PhoneState.SILENT) {
            // Do Notification action in vibrate
            console.log(message);
        }else {
            // Default Notification action
            console.log("{{Ring}}", "{{Vibrate}}", message);
        }
    }
}
/** 
 * States that a Phone can be in
*/
export enum PhoneState {
    RING_AND_VIBRATE, // Ringing and Vibrate mode
    RING, // Ring mode
    VIBRATE, // Vibrate Mode
    SILENT, // Silent Mode
    AIRPLANE // Airplane Mode
}
function main() {
    const myPhone = new Phone();
    console.log("Checking Notification... ", myPhone.getNotification());
    myPhone.pushNotification("Hello");
    console.log("Checking Notification... ", myPhone.getNotification());
    myPhone.setMode(PhoneState.SILENT);
    myPhone.pushNotification("Hello Again");
    console.log("Checking Notification... ", myPhone.getNotification());

}
main();