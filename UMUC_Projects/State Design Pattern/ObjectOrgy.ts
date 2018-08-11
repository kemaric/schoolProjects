abstract class Animal{
    private name: string; // preventing outside class access 
    private birthdate: Date; // preventing outside class access

    public setBirthdate(newDate: Date){
        const currDate = new Date(Date.now());
        if(newDate != null && newDate <= currDate) {
            this.birthdate = newDate; //Ensuring a non null, past date for birthdate
        }
    }
    public getBirthdate(){
        return new Date(this.birthdate); // Returing a copy of the birthdate instead of the reference
    }
    public setName(newName){
        this.name = newName;  // Problem: Directly setting the name in this setter
    }
    public getName(){
        return this.name; // Problem: Sending a reference to the name property
    }
    public age() {
        return (new Date(Date.now())).getFullYear() - (this.getBirthdate().getFullYear());
    }
    constructor(_name: string, dob: Date){
        this.name = name;
        this.birthdate = dob;
    }
}
abstract class Bird extends Animal{
    constructor(name: string, birthdate: Date){
        super(name,birthdate);
    }
}
class Eagle extends Animal{
    constructor(name: string, birthdate: Date){
        super(name,birthdate);
    }
}

// Zoo Related classes
class Zoo{
    private animals: Set<Animal>;
    private readonly id: number;
    getZooId(){
        return this.id;
    } 
    getZooAnimals(){
        return new Set(this.animals);
    }
    addAnimal(animal: Animal){
        this.animals.add(animal);
    }
    removeAnimal(animal: Animal){
        this.animals.delete(animal);
    }
    constructor(_animals?: Set<Animal>){
        if(_animals != null){
            this.animals = _animals;
        }else{
            this.animals = new Set();
        }
    }
}

class ZooOrganizer {
    private zoos: Set<Zoo>;
    getZoos(){
        return this.zoos;
    }
    addZoo(zoo: Zoo){
        this.zoos.add(zoo);
    }
    removeZoo(zoo: Zoo){
        this.zoos.delete(zoo);
    }
    constructor(_zoos?: Set<Zoo>){
        if(_zoos != null){
            this.zoos = _zoos;
        }else{
            this.zoos = new Set();
        }
    }

}

function main(){
    const myZooOrganizer = new ZooOrganizer();
}