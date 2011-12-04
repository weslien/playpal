package controllers;

import play.mvc.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: gustav
 * Date: 2011-12-02
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
public class Leaf extends Controller{

    public static void view(int id){

        //Load leafModel

        //Find all classes with BeforeLeafLoaded watches
        //LeafHelper.dispatchBeforeLoaded


        render();
    }
}
