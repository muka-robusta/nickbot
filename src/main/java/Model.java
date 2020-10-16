import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Model {

    private String name;
    private Double temp;
    private Double humidity;
    private String icon;
    private String main;

    @Override
    public String toString() {
        return "Погода у" +
                " " + name +
                ", температура: " + temp + " градусів по цельсію" +
                ", вологість: " + humidity +
                ", опис: " + main + "\n" +
                "https://openweathermap.org/img/w/" + icon + ".png";
    }
}
