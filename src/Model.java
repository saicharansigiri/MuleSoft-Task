
class MovieModel {
    String name;
    String leadActor;
    String leadActress;
    int year_released;
    String directorName;

    public MovieModel(String name, String leadActor, String leadActress, int year_released, String directorName) {
        this.name = name;
        this.leadActor = leadActor;
        this.leadActress = leadActress;
        this.year_released = year_released;
        this.directorName = directorName;
    }
}
