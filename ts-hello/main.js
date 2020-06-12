var Point = /** @class */ (function () {
    // private x: number;
    // y: number;
    function Point(x, y) {
        this.x = x;
        this.y = y;
    }
    Point.prototype.draw = function () {
        console.log(this.x + " , " + this.y);
    };
    Point.prototype.getDist = function (another) {
    };
    Object.defineProperty(Point.prototype, "X", {
        get: function () { return this.x; },
        set: function (val) {
            this.x = val;
        },
        enumerable: false,
        configurable: true
    });
    return Point;
}());
var point = new Point(1, 2);
// point.draw = function() {
//     console.log('haha');
// }
point.draw();
point.X = 44;
point.draw();
