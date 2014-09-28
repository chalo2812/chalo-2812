<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asignación Usuario-Flux IT</title>
</head>
<body>
	<form action="asignacionUsuario"  >
		<table bgcolor="#00CCA3" align="center" width="80%">
			<tr>
				<td><p style="padding-left: 10px;">
						Adjunta el documento seleccionado<b style="color: red">*</b> 
						<input type="file" id="archivo" tabindex="" name="archivo"
							title="Seleccione un archivo - Obligatorio" value="" /><br>
					</p></td>
			</tr>
			<tr>
				<td>
					<p style="padding-left: 10px;">
						Seleccione el tipo de documento<b style="color: red">*</b> <select
							id="tipoDocumento">
							<option value="" selected="selected">Seleccionar</option>
							<option value="1">Público</option>
							<option value="2">Privado</option>
							<option value="3">Draft</option>
						</select>
				</td>
			</tr>
			<tr>
				<td align="center" style="padding-left: 10px;"><textarea
						rows="7" cols="80" id="descripcion" style=""></textarea></td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
		</table>
		<table>
			<tr></tr>
		</table>
		<input type="submit" onclick="validar(this);">
	</form>

</body>
</html>

<script type="text/javascript">
	function validar() {

		var formato = document.forms[0];
		var archivo = formato[0].value;
		var tipoDocumento = formato[1].value;
		var descripcion = formato[2].value;

		document.forms[0].archivo = archivo;
		document.forms[0].tipoDocumento = tipoDocumento;
		document.forms[0].descripcion = descripcion;

		document.forms[0].reqCode.value = "guardar";
		document.forms[0].submit();

	}
</script>
