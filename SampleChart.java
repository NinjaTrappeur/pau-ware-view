/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import com.pauware.pauware_engine._Core.AbstractStatechart;
import com.pauware.pauware_engine._Core.AbstractStatechart_monitor;
import com.pauware.pauware_engine._Exception.Statechart_exception;
import com.pauware.pauware_engine._Java_EE.Statechart;
import com.pauware.pauware_engine._Java_EE.Statechart_monitor;

/**
 *
 * @author josuah
 */
public class SampleChart {

    /**
     * Business data
     */
    private SampleChartActions _actions = new SampleChartActions();
    /**
     * UML state machine
     */
    AbstractStatechart _Idle;
    AbstractStatechart _S11;
    AbstractStatechart _S12;
    AbstractStatechart _S21;
    AbstractStatechart _S22;
    AbstractStatechart _S31;
    AbstractStatechart _S32;
    AbstractStatechart _S1;
    AbstractStatechart _S2;
    AbstractStatechart _S3;
    AbstractStatechart _Busy;
    AbstractStatechart_monitor _PauWare_component;

    public SampleChart() throws Statechart_exception
    {
        _Idle = new Statechart("Idle");
        _Idle.inputState();
        _Idle.stateInvariant(_actions, "isClosed");
        _S11 = ((new Statechart("S11")).set_entryAction(this, "w")).set_exitAction(this, "x");
        _S11.inputState();
        _S12 = ((new Statechart("S12")).set_entryAction(this, "y")).set_exitAction(this, "z");
        _S21 = new Statechart("S21");
        _S22 = new Statechart("S22").set_entryAction(this, "request_h", null, AbstractStatechart.Reentrance);
        _S22.inputState();
        _S31 = new Statechart("S31");
        _S32 = new Statechart("S32");
        _S1 = (_S11.xor(_S12)).name("S1");
        _S2 = (_S21.xor(_S22)).name("S2");
        _S3 = (_S31.and(_S32)).name("S3");
        _Busy = (_S1.and(_S2)).and(_S3).name("Busy");
        Object[] args = new Object[1];
        args[0] = true;
        _Busy.set_entryAction(_actions, "setOpen", args);
        _PauWare_component = new Statechart_monitor(_Idle.xor(_Busy), "PauWare component", AbstractStatechart_monitor.Don_t_show_on_system_out);
    }

    public void start() throws Statechart_exception {
        _PauWare_component.fires("go", _Idle, _Busy);
        _PauWare_component.fires("request_b", _S1, _S12);
        _PauWare_component.fires("request_c", _Busy, _S12);
        _PauWare_component.fires("request_d", _Busy, _S21);
        _PauWare_component.fires("request_e", _S2, _Idle);
        _PauWare_component.fires("request_f", _S21, _Idle);
        _PauWare_component.fires("request_g", _S12, _S11);
        _PauWare_component.fires("request_h", _S32, _S32, true, this, "a");
        /**
         * PauWare view
         */
        com.PauWare.PauWare_view.Statechart_monitor_viewer statechart_monitor_viewer = new com.PauWare.PauWare_view.Statechart_monitor_viewer();
        _PauWare_component.add_listener(statechart_monitor_viewer);
        _PauWare_component.initialize_listener();

        _PauWare_component.start();
    }

    /**
     * UML events
     */
    public void request_b() throws Statechart_exception {
        _PauWare_component.run_to_completion("request_b");
    }

    synchronized public void request_c() throws Statechart_exception {
        _PauWare_component.fires(_Busy, _S22);
        _PauWare_component.run_to_completion("request_c");
    }

    public void request_d() throws Statechart_exception {
        _PauWare_component.run_to_completion("request_d");
    }

    public void request_e() throws Statechart_exception {
        _PauWare_component.run_to_completion("request_e", AbstractStatechart_monitor.Compute_invariants);
    }

    public void request_f() throws Statechart_exception {
        _PauWare_component.run_to_completion("request_f");
    }

    public void request_g() throws Statechart_exception {
        _PauWare_component.run_to_completion("request_g");
    }

    public void request_h() throws Statechart_exception {
        _PauWare_component.run_to_completion("request_h");
    }

    public void go() throws Statechart_exception {
        _PauWare_component.run_to_completion("go", AbstractStatechart_monitor.Compute_invariants);
    }

    /**
     * UML actions
     */
    public void a() {
        System.out.println("a activated");
    }

    public void w() {
        System.out.println("w activated");
    }

    public void x() {
        System.out.println("x activated");
    }

    public void y() {
        System.out.println("y activated");
    }

    public void z() {
        System.out.println("z activated");
    }
    
    public AbstractStatechart_monitor getStateMachine()
    {
        return _PauWare_component;
    }
}
