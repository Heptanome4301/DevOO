package vue;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZoomListener implements ChangeListener {
	
	private VueGraphique view;
	
	public ZoomListener(VueGraphique view) {
		this.view = view;
	}
	
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider) e.getSource();
	        if (!source.getValueIsAdjusting()) {
	            view.setZoom(source.getValue() / 100.);
	            view.repaint();
	        }    
		}
}