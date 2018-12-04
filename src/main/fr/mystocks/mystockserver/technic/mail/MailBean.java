package fr.mystocks.mystockserver.technic.mail;

import java.io.File;
import java.util.Map;

/**
 * Bean représentant un mail
 * 
 * @author FFBB
 */
public class MailBean {

    private String receiver;

    private String sender;

    private String subject;

    private String body;

    private Boolean blHtml;

    private Map<String, File> files;

    private Map<String, byte[]> filesByteArray;

    private byte[] data;

    private Boolean isDonneesJointsByteArray;

    private String nomFichier;

    /**
     * .
     * 
     * @param receiver
     *            le destinataire
     * @param sender
     *            l'envoyeur
     * @param subject
     *            le sujet
     * @param body
     *            le corps
     * @param blHtml
     *            présence d'html ?
     * @param files
     *            les fichiers
     * @param data
     *            les donnees en format byte array
     * @param nomFichier
     *            Le nom du fichier ci joint
     * @param isDonneesJointsByteArray
     *            les donnÃ©es en format byte array?
     */
    public MailBean(String receiver, String sender, String subject, String body, Boolean blHtml,
	    Map<String, File> files, Map<String, byte[]> filesByteArray, Boolean isDonneesJointsByteArray) {
	super();
	this.receiver = receiver;
	this.sender = sender;
	this.subject = subject;
	this.body = body;
	this.blHtml = blHtml;
	this.files = files;
	this.isDonneesJointsByteArray = isDonneesJointsByteArray;
	this.filesByteArray=filesByteArray;
    }

    /** . @return the files */
    public Map<String, File> getFiles() {
	return files;
    }

    /** . @param files the files to set */
    public void setFiles(Map<String, File> files) {
	this.files = files;
    }

    /** . @return the receiver */
    public String getReceiver() {
	return receiver;
    }

    /** . @param receiver the receiver to set */
    public void setReceiver(String receiver) {
	this.receiver = receiver;
    }

    /** . @return the sender */
    public String getSender() {
	return sender;
    }

    /** . @param sender the sender to set */
    public void setSender(String sender) {
	this.sender = sender;
    }

    /** . @return the subject */
    public String getSubject() {
	return subject;
    }

    /** . @param subject the subject to set */
    public void setSubject(String subject) {
	this.subject = subject;
    }

    /** . @return the body */
    public String getBody() {
	return body;
    }

    /** . @param body the body to set */
    public void setBody(String body) {
	this.body = body;
    }

    /** . @return the blHtml */
    public Boolean getBlHtml() {
	return blHtml;
    }

    /** . @param blHtml the blHtml to set */
    public void setBlHtml(Boolean blHtml) {
	this.blHtml = blHtml;
    }

    /** . @return the data */
    public byte[] getData() {
	return data;
    }

    /** . @param data the data to set */
    public void setData(byte[] data) {
	this.data = data;
    }

    /** . @return the isDonneesJointsByteArray */
    public Boolean getIsDonneesJointsByteArray() {
	return isDonneesJointsByteArray;
    }

    /** . @param isDonneesJointsByteArray the isDonneesJointsByteArray to set */
    public void setIsDonneesJointsByteArray(Boolean isDonneesJointsByteArray) {
	this.isDonneesJointsByteArray = isDonneesJointsByteArray;
    }

    /** . @return the nomFichier */
    public String getNomFichier() {
	return nomFichier;
    }

    /** . @param nomFichier the nomFichier to set */
    public void setNomFichier(String nomFichier) {
	this.nomFichier = nomFichier;
    }

    /**
     * @return the filesByteArray
     */
    public Map<String, byte[]> getFilesByteArray() {
	return filesByteArray;
    }

    /**
     * @param filesByteArray
     *            the filesByteArray to set
     */
    public void setFilesByteArray(Map<String, byte[]> filesByteArray) {
	this.filesByteArray = filesByteArray;
    }

}
