package login.client;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class Login implements EntryPoint {

	private TextBox username = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();;
	private Label exitoso;

	public void onModuleLoad() {
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setBorderWidth(1);

		Label etiqueta = new Label("Login");
		etiqueta.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		etiqueta.setDirectionEstimator(false);
		vPanel.add(etiqueta);
		vPanel.setCellHeight(etiqueta, "30");
		vPanel.setCellVerticalAlignment(etiqueta,
				HasVerticalAlignment.ALIGN_MIDDLE);
		etiqueta.setSize("20", "40");
		vPanel.setCellHorizontalAlignment(etiqueta,
				HasHorizontalAlignment.ALIGN_CENTER);

		VerticalPanel textos = new VerticalPanel();
		textos.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		HorizontalPanel hTextosU = new HorizontalPanel();
		HorizontalPanel hTextosP = new HorizontalPanel();
		Label user = new Label("Nombre de Usuario:");
		Label pass = new Label("Contrasena:");
		hTextosU.add(user);
		hTextosU.setCellVerticalAlignment(user,
				HasVerticalAlignment.ALIGN_MIDDLE);
		hTextosU.add(username);
		hTextosU.setCellVerticalAlignment(username,
				HasVerticalAlignment.ALIGN_MIDDLE);
		username.setSize("165", "32");
		hTextosP.add(pass);
		hTextosP.setCellVerticalAlignment(pass,
				HasVerticalAlignment.ALIGN_MIDDLE);
		hTextosP.add(password);
		hTextosP.setCellVerticalAlignment(password,
				HasVerticalAlignment.ALIGN_MIDDLE);
		password.setSize("165", "32");
		textos.add(hTextosU);
		textos.setCellVerticalAlignment(hTextosU,
				HasVerticalAlignment.ALIGN_MIDDLE);
		hTextosU.setHeight("42px");
		textos.add(hTextosP);
		textos.setCellVerticalAlignment(hTextosP,
				HasVerticalAlignment.ALIGN_MIDDLE);
		hTextosP.setHeight("42px");
		vPanel.add(textos);

		HorizontalPanel hPanel = new HorizontalPanel();

		Button ingresar = new Button("Ingresar");
		ingresar.addClickHandler(new ButtonIngresar());
		hPanel.add(ingresar);
		ingresar.setWidth("100px");
		hPanel.setCellVerticalAlignment(ingresar,
				HasVerticalAlignment.ALIGN_MIDDLE);
		Button cancelar = new Button("Cancelar");
		hPanel.add(cancelar);
		cancelar.setWidth("100px");
		hPanel.setCellVerticalAlignment(cancelar,
				HasVerticalAlignment.ALIGN_MIDDLE);

		vPanel.add(hPanel);
		hPanel.setHeight("40px");

		this.exitoso = new Label("");
		vPanel.add(exitoso);

		RootPanel.get().add(vPanel);
	}

	// private class ButtonIngresar implements ClickHandler {
	//
	// @Override
	// public void onClick(ClickEvent event) {
	//
	// String nombreDeUsuario = username.getText();
	// String contrasenia = password.getText();
	//
	// try {
	// Connection conn = ConnManager.getConnection();
	//
	// String qry = "select * from usuario where password = \""
	// + contrasenia + "\" and usuario_name = \""
	// + nombreDeUsuario + "\"";
	// PreparedStatement pst = conn.prepareStatement(qry);
	// ResultSet rs = pst.executeQuery(qry);
	//
	// pst.close();
	// conn.close();
	//
	// if (rs.getString("usuario_name") == nombreDeUsuario)
	// exitoso.setText("Login EXITOSO");
	// } catch (Exception e) {
	// exitoso.setText("ERROR. Nombre de Usuario y/o Contraseña incorrectos");
	// }
	//
	// }
	//
	// }
	// ////////////////////////////////////////

	private class ButtonIngresar implements ClickHandler {
		public void onClick(ClickEvent event) {
			ConnManager userAuthenticationConnection = new ConnManager();
			ResultSet rs = null;
			boolean test = userAuthenticationConnection.doConnection();
			if (test == true) {
				Connection con = userAuthenticationConnection.getConnect();
				PreparedStatement stmt = null;
				String sql = "select password from users where user_name = ?";
				try {
					stmt = con.prepareStatement(sql);
					String nombreDeUsuario = username.getText();
					stmt.setString(1, nombreDeUsuario);
					rs = stmt.executeQuery();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String passWord = null;
				try {
					passWord = rs.getString("password");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String contrasenia = password.getText();
				if (passWord.equals(contrasenia)) {
					exitoso.setText("Login EXITOSO");
					userAuthenticationConnection.closeConnection();
				}
			}
		}
	}
}