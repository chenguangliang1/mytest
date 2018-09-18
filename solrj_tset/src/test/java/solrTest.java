import cn.itcast.solr.pojo.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

import java.util.List;

public class solrTest {
    private SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

    @Test
    public void saveOrUpdate()throws Exception{
        Product product = new Product();
        product.setPid("8000");
        product.setName("iphone8");
        product.setCatalogName("手机");
        product.setPrice(8000);
        product.setDescription("还可以吧!");
        product.setPicture("1.jpg");
        solrServer.addBean(product);
        solrServer.commit();
    }
    @Test
    public void deleteById()throws  Exception{
        solrServer.deleteById("8000");
        solrServer.commit();
    }
    @Test
    public void deleteByQuery()throws  Exception{
        //solrServer.deleteByQuery("name:手机");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
    @Test
    public void query()throws Exception{
        SolrQuery sq = new SolrQuery("*:*");
        sq.setStart(10);
        sq.setRows(10);
        QueryResponse response = solrServer.query(sq);
        System.out.println("搜索总得到数量: "+response.getResults().getNumFound());
        List<Product> products = response.getBeans(Product.class);
        for (Product product : products) {
            System.out.println("==============");
            System.out.println(product.getPid()+"\t"+product.getName()+"\t"+product.getCatalogName());
        }
    }
}
