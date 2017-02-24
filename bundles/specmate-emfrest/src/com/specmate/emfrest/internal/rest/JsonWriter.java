package com.specmate.emfrest.internal.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.emf.ecore.EObject;

import com.specmate.emfjson.EMFJsonSerializer;
import com.specmate.emfrest.internal.util.EmfRestUtil;
import com.specmate.model.support.urihandler.IURIFactory;

/** Serializes EMF object to JSON */
public class JsonWriter {

	public static final String MEDIA_TYPE = MediaType.APPLICATION_JSON + ";charset=utf-8";

	/** URI factory to obtain URIs from EObjects */
	private IURIFactory factory;

	/** constructor */
	public JsonWriter(IURIFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} */
	public long getSize(Object obj, Class<?> clazz, Type type, Annotation[] annotation, MediaType mediaType) {
		return -1;
	}

	/** {@inheritDoc} */
	public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotation, MediaType mediaType) {
		return mediaType.toString().equals(MEDIA_TYPE)
				&& (EObject.class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	public void writeTo(Object obj, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> headers, OutputStream stream) throws IOException {
		EMFJsonSerializer serializer = new EMFJsonSerializer(factory);
		String result = null;
		if (obj instanceof EObject) {
			try {
				result = serializer.serialize((EObject) obj).toString();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
		} else if (obj instanceof List) {
			try {
				result = serializer.serialize((List<?>) obj).toString();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
		} else {
			EmfRestUtil.throwBadRequest("Cannot serialize " + clazz);
		}

		OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8");
		writer.write(result);
		writer.flush();
	}

}