package com.specmate.model.support.internal;

import static com.specmate.model.support.util.SpecmateEcoreUtil.getEObjectWithId;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.specmate.model.support.urihandler.IObjectResolver;

@Component(service = IObjectResolver.class)
public class EObjectUriResolver implements IObjectResolver {

	private static final String VIEWS = "_views";

	private enum SegmentType {
		ID, FEATURE, VIEW
	}

	public EObjectUriResolver() {
	}

	@Override
	public EObject getObject(String uri, Resource resource) {
		SegmentType segType = SegmentType.ID;
		List<String> segments = Arrays.asList(StringUtils.split(uri, "/"));
		EObject object = null;
		List<EObject> candidates = resource.getContents();
		for (int i = 0; i < segments.size(); i++) {
			String currentSegment = segments.get(i);
			if (currentSegment.isEmpty()) {
				continue;
			}
			if (segType == SegmentType.ID) {
				object = getEObjectWithId(currentSegment, candidates);
				if (object == null) {
					return null;
				}
				segType = SegmentType.FEATURE;
			} else {

				EStructuralFeature feature = object.eClass().getEStructuralFeature(currentSegment);
				if (feature == null || feature instanceof EAttribute) {
					return null;
				}
				if (feature.isMany()) {
					candidates = (List<EObject>) object.eGet(feature);
				} else {
					candidates = Arrays.asList((EObject) object.eGet(feature));
				}
				segType = SegmentType.ID;

			}

		}
		return object;
	}

}