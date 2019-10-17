package conversores;

import controle.CategoriaControle;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import modelo.Categoria;

/**
 *
 * @author professor
 */
@FacesConverter("categoriaConverter")
public class CategoriaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = new Long(value);
        CategoriaControle controle = (CategoriaControle) context.getApplication().
                getELResolver().getValue(context.getELContext(), null, "categoriaControle");
        
        return controle.buscarCategoriaPorId(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        Categoria categoria = (Categoria) value;
        
        return categoria.getId() == null ? "" : categoria.getId().toString();
    }
    
}
