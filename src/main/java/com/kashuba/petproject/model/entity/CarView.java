package com.kashuba.petproject.model.entity;

/**
 * The Car view.
 * <p>
 * To provide more detailed information to the client and to help
 * him with the choice of a car, View stores data on the images
 * of a particular car to provide visualization on the client side.
 *
 * @author Balashevich Gleb
 * @version 1.0
 */
public class CarView extends Entity{
    private String exterior;
    private String exteriorSmall;
    private String interior;

    /**
     * Gets exteriorSmall.
     *
     * @return the exterior small
     */
    public String getExteriorSmall() {
        return exteriorSmall;
    }

    /**
     * Sets exteriorSmall.
     *
     * @param exteriorSmall the exterior small
     */
    public void setExteriorSmall(String exteriorSmall) {
        this.exteriorSmall = exteriorSmall;
    }

    /**
     * Gets exterior.
     *
     * @return the exterior
     */
    public String getExterior() {
        return exterior;
    }

    /**
     * Sets exterior.
     *
     * @param exterior the exterior
     */
    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    /**
     * Gets interior.
     *
     * @return the interior
     */
    public String getInterior() {
        return interior;
    }

    /**
     * Sets interior.
     *
     * @param interior the interior
     */
    public void setInterior(String interior) {
        this.interior = interior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        com.kashuba.petproject.model.entity.CarView carView = (com.kashuba.petproject.model.entity.CarView) o;

        return (exterior != null && exterior.equals(carView.exterior))
                && (exteriorSmall != null && exteriorSmall.equals(carView.exteriorSmall))
                && (interior != null && interior.equals(carView.interior));
    }

    @Override
    public int hashCode() {
        int result = 1;
        result += 37 * result + (exterior == null ? 0 : exterior.hashCode());
        result += 37 * result + (exteriorSmall == null ? 0 : exteriorSmall.hashCode());
        result += 37 * result + (interior == null ? 0 : interior.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return String.format("View: exterior %s, exteriorSmall %s, interior %s", exterior, exteriorSmall, interior);
    }
}
