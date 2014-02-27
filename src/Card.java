
public class Card {

    /* Card Object: each of these represents one of the cards in the deck.
    Values are represented as integers for easy comparison. See comments below
    for details as per value mapping.
     */

    int color, shape, quantity, shade;

    public Card(int color, int shape, int shade, int quantity) {
        this.color = color; // 0 = Green, 1 = Purple, 2 = Red
        this.shade = shade; // 0 = Empty, 1 = Solid, 2 = Striped
        this.shape = shape; // 0 = Diamond, 1 = Oval, 2 = Squiggle
        this.quantity = quantity;
    }

    // Getter method, returns the 4 features of a card in an array

    public int[] getFeatures() {
        int[] featureArray = {color, shape, shade, quantity};
        return featureArray;
    }

    /* This method will take a Card object with quantities defined, read those
    values, and return a String that corresponds to the file name of an
    image of that particular Card.
     */

    public String getImageName() {
        String colorString, shapeString, shadeString, quantityString;

        switch (color) {
            case 0:
                colorString = "Green_";
                break;
            case 1:
                colorString = "Purple_";
                break;
            case 2:
                colorString = "Red_";
                break;
            default:
                colorString = "color:null_";
        }

        switch (shape) {
            case 0:
                shapeString = "Diamond_";
                break;
            case 1:
                shapeString = "Solid_";
                break;
            case 2:
                shapeString = "Squiggle_";
                break;
            default:
                shapeString = "shape:null_";
        }

        switch (shade) {
            case 0:
                shadeString = "Empty";
                break;
            case 1:
                shadeString = "Solid";
                break;
            case 2:
                shadeString = "Striped";
                break;
            default:
                shadeString = "shade:null_";
        }

        switch (quantity) {
            case 0:
                quantityString = "01_";
                break;
            case 1:
                quantityString = "02_";
                break;
            case 2:
                quantityString = "03_";
                break;
            default:
                quantityString = "quantity:null_";
        }

        return (quantityString + colorString + shapeString + shadeString + ".gif");
    }
}

