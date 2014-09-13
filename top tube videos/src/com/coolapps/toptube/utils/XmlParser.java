package com.coolapps.toptube.utils;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/*
 * Adapter que parseel fichero de configuracion
 */
public class XmlParser extends DefaultHandler {

	// ===========================================================
	// Fields
	// ===========================================================
	private enum Tags {
		xml, categorys, lenght, category, tag,news, icon, video, image, favorit, contact, email, index, bigtext,smalltext, title, url, playlist
	}

	private static final String tag1 = "test";
	private boolean categorys = false;
	private boolean category = false;
	private boolean video = false;
	private boolean playlist = false;
	private boolean index = false;
	private boolean news = false;
	private boolean tag = false;
	private boolean favorit = false;
	private boolean contact = false;
	private boolean email = false;
	private boolean icon = false;
	private boolean smalltext = false;
	private boolean bigtext = false;
	private boolean image = false;
	private boolean url = false;
	private boolean title = false;
	private boolean lenght = false;
	// ////////////////////////////////////////////////////////////
	private Category categoryModel;
	private Model model;
	private Content ContentModel;
	// //////////////////////////////////////////////////////////
	String type;
	List<Category> listacategory = new ArrayList<Category>();
	private ArrayList<String> list = new ArrayList<String>();
	private int totalRows;

	public int getTotalRows() {
		return totalRows;
	}

	public Model getModel() {
		return model;
	}

	public int getTotalColumns() {
		return totalColumns;
	}

	private int totalColumns;

	private String nm;

	public XmlParser() {
		super();

	}

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		// this.myParsedExampleDataSet = new ParsedExampleDataSet();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
		
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		switch (Tags.valueOf(localName)) {
		case lenght:
			lenght = true;
			break;
		case categorys:
			model = new Model();
			categorys = true;
			break;
		case category:
			categoryModel = new Category();
			category = true;
			break;
		case video:
			ContentModel = new Content();
			ContentModel.type = "video";
			type = "video";
			video = true;
			break;
		case tag:
			tag = true;
			break;
		case playlist:
			playlist = true;
			break;
		case icon:
			icon = true;
			break;
		case image:
			ContentModel = new Content();
			ContentModel.type = "image";
			type = "image";
			image = true;
			break;
		case contact:
			ContentModel = new Content();
			ContentModel.type = "contact";
			type = "contact";
			contact = true;
			break;
		case favorit:
			ContentModel = new Content();
			ContentModel.type = "favorit";
			type = "favorit";
			favorit = true;
			break;
		case email:
			email = true;
			break;
		case index:
			index = true;
			break;
		case smalltext:
			smalltext = true;
			break;
		case bigtext:
			bigtext = true;
			break;
		case url:
			url = true;
			break;
		case news:
			news = true;
			break;
		case title:
			title = true;
			break;

		default:
			break;
		}

	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		switch (Tags.valueOf(localName)) {
		case lenght:
			lenght = false;
			break;
		case categorys:
			// model.bloq=listacategory;
			categorys = false;
			break;
		case category:
			// categoryModel.videos=listavideo;
			// listavideo.clear();
			model.bloq.add(categoryModel);
			category = false;
			break;
		case video:
			categoryModel.contents.add(ContentModel);
			categoryModel.type = type;
			// listavideo.add(videoModel);
			video = false;
			break;
		case image:
			categoryModel.contents.add(ContentModel);
			categoryModel.type = type;
			image = false;
			break;
		case contact:
			categoryModel.contents.add(ContentModel);
			categoryModel.type = type;
			contact = false;
			break;
		case favorit:
			categoryModel.contents.add(ContentModel);
			categoryModel.type = type;
			favorit = false;
			break;
		case index:
			index = false;
			break;
		case news:
			news = false;
			break;
		case tag:

			tag = false;
			break;
		case playlist:

			playlist = false;
			break;
		case email:
			email = false;
			break;
		case icon:
			icon = false;
			break;
		case url:
			url = false;
			break;
		case bigtext:
			bigtext = false;
			break;
		case smalltext:
			smalltext = false;
			break;
		case title:
			title = false;
			break;
		default:
			break;
		}
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		if (lenght) {
			model.lenght = Integer.parseInt(new String(ch, start, length));
		}
		if (tag) {
			categoryModel.tag = new String(ch, start, length);
		}
		if (icon) {
			categoryModel.icon = new String(ch, start, length);
		}
		if (playlist) {
			categoryModel.playlist = new String(ch, start, length);
		}
		if (favorit) {
			ContentModel.favorit = new String(ch, start, length);
		}
		if (contact) {
			if (email) {

				ContentModel.email = new String(ch, start, length);
				Log.w("test", "email :" + ContentModel.email);
			}
		}
		if (video || image) {
			try {
				if (this.index) {
					ContentModel.index = Integer.parseInt(new String(ch, start,
							length));
				}
				if (this.url) {

					ContentModel.url = new String(ch, start, length);
				}
				if (this.smalltext) {
					ContentModel.smalltext = new String(ch, start, length);
				}
				if (this.bigtext) {
					ContentModel.bigtext = new String(ch, start, length);
				}
				if (this.title) {
					ContentModel.title = new String(ch, start, length);
					// Aqui es donde hago la evaluación
				}if (this.news) {
					ContentModel.news = Boolean.valueOf(new String(ch, start, length));
					// Aqui es donde hago la evaluación
				}

			} catch (NumberFormatException e) {
				Log.e("test", "row/column malformed");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}