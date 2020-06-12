export class Point {
    // private x: number;
    // y: number;
    constructor(private _x?: number, private _y? : number){

    }
    draw() {
        console.log(this._x + " , " + this._y);
    }
    getDist(another: Point) {

    }
    
    get x() {return this._x;}
    set x(val) {
        this._x = val;
    }
    

}