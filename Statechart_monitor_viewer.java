/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

public class Statechart_monitor_viewer extends processing.core.PApplet implements com.pauware.pauware_engine._Core.Statechart_monitor_listener {

    /**
     * PauWare
     */
    com.pauware.pauware_engine._Core.AbstractStatechart_monitor _state_machine = null;
    String _trace = "";
    /**
     * Swing
     */
    javax.swing.JFrame _state_machine_view;

    Painter _painter;
    ILayout _layout;
    ILayoutProcessor _layoutProcessor;
    IChart _chart;
    
    public Statechart_monitor_viewer()
    {
    }
    
    @Override
    public void initialize(com.pauware.pauware_engine._Core.AbstractStatechart_monitor state_machine) {
        // Cette méthode est appelée une seule fois à l'initialisation de la 'state machine'.
        // Ce qui est à faire, c'est de balayer l'arbre binaire de représentation de la 'state machine'
        // et le dessiner à l'écran dans la méthode 'setup' ci-dessous.
        _state_machine = state_machine;
        if (_state_machine != null) {
            _state_machine_view = new javax.swing.JFrame(_state_machine.name());
            _state_machine_view.setSize(900, 900);
            _state_machine_view.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//            javax.swing.JPanel panel = new javax.swing.JPanel();
//            panel.setBounds(20, 20, 600, 800);
//            panel.add(this);
            _state_machine_view.add(this); // _state_machine_view.add(panel);
            this.init(); // This is the function used to start the execution of any Processing sketch
            _state_machine_view.setVisible(true);
        }
    }

    @Override
    public void run_to_completion(String trace) {
        // A chaque cycle de la 'state machine', on récupère la 'trace' de l'exécution
        _trace = trace;
    }

    @Override
    public void run_to_completion(java.util.Hashtable transitions) {
        // A chaque cycle de la 'state machine', on récupère les transitions qui ont été déclenchées.
        // Chaque clef de la table de hachage est de type 'com.pauware.pauware_engine._Core.Transition'.

        // On rafraichit l'écran que s'il se passe quelque chose dans la machine à états :
        redraw();
    }

    @Override
    public void setup() {
        // Cette méthode est appelée à la création de la fenêtre Processing.
        size(600, 800);
        background(200);
        if (_state_machine != null) {
            _chart = new StateChart(_state_machine,"StateChart",300,300);
            _layoutProcessor = new JungLayoutProcessor();
            _layoutProcessor.init(_chart);
            _layout = _layoutProcessor.getLayout();
            _painter = new Painter(_chart,_layout,this);
        }
        // Inutile de redessiner l'écran s'il ne se passe rien dans la machine à états :
        //noLoop();
    }

    @Override
    public void draw() {
        // Cette méthode est appelée en boucle par Processing.
        background(200);
        if (_trace != null) {
            System.out.print("coucou");
            _layoutProcessor.processLayout();
            _layout=_layoutProcessor.getLayout();
            _painter.paint();
        }
    }
}
