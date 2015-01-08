package jp.tearoom6.api.rest;


import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import jp.tearoom6.api.rest.memorytouch.model.RankingRecord;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

/**
 * JAXB (内装はJackson1) の JSONシリアライズ / デシリアライズ の際の設定をカスタマイズ
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private JAXBContext context;
    // この設定を有効にする対象変換オブジェクト
    private Class[] types = { RankingRecord.class };

    public JAXBContextResolver() throws Exception {
        // リスト数が1でも[]で囲む(リスト扱い)、RootElementはJSONに含めない
        this.context = new JSONJAXBContext(JSONConfiguration.natural().rootUnwrapping(true).build(),
                types);
    }

    public JAXBContext getContext(Class objectType) {
        for (Class type : types) {
            if (type == objectType) {
                return context;
            }
        }
        return null;
    }

}
