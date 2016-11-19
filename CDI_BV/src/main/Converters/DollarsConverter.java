package main.Converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.DecimalFormat;

@FacesConverter("DollarsConverter")
public class DollarsConverter implements Converter {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value)
        {
            return value;
        }
        @Override
        public String getAsString(FacesContext ctx, UIComponent component, Object value) {
            float amountInDollars = Float.parseFloat(value.toString());
            double ammountInRub = amountInDollars / 2;
            DecimalFormat df = new DecimalFormat("###,##0.##$");
            return df.format(ammountInRub);
        }
}
