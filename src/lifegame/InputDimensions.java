package lifegame;

import javax.swing.JOptionPane;

public class InputDimensions {
	private String dimensionStr;
	public int dimension;
	private boolean validInput;
	
	public int getDimension(String typeOfDimension) {
		do {
			validInput = true;
			dimensionStr = JOptionPane.showInputDialog("Number of " + typeOfDimension + ": ");
			try {
				dimension = Integer.parseInt(dimensionStr);
			}
			catch(NumberFormatException e){
				validInput = false;
			}
		}while(!validInput || dimension <= 0);
		
		return dimension;
	}
}
