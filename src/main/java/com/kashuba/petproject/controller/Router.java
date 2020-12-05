package com.kashuba.petproject.controller;

/**
 * The Router.
 * <p>
 * Used to store the path to the page to which the controller will redirect.
 * In addition to the path, the router contains information about the type
 * of transition redirect or forward
 *
 * @author Kiryl Kashuba
 * @version 1.0
 */
public class Router {
    /**
     * The enum Transition.
     */
    public enum Transition {
        /**
         * Forward transition.
         */
        FORWARD,
        /**
         * Redirect transition.
         */
        REDIRECT
    }

    private Transition transition = Transition.FORWARD;
    private String page;

    /**
     * Instantiates a new Router.
     *
     * @param page the page
     */
    public Router(String page) {
        this.page = page;
    }

    /**
     * Instantiates a new Router.
     *
     * @param page       the page
     * @param transition the transition
     */
    public Router(String page, Transition transition) {
        this.page = page;
        this.transition = transition;
    }

    /**
     * Gets transition type.
     *
     * @return the transition
     */
    public Transition getTransition() {
        return transition;
    }

    /**
     * Gets page path.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets page path.
     *
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }
}
