package com.ydw.es.controller;

import com.ydw.es.entity.User;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author ydw
 */
@RestController
@RequestMapping("/user")
public class TestController {

    @Autowired
    private Client client;
    @PostMapping()
    public User addUser(@RequestBody User user){
            try {
                // 使用XContentBuilder创建一个doc source
                XContentBuilder builder =
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("name", user.getName())
                                .field("age", user.getAge())
                                .endObject();

                IndexResponse indexResponse = this.client
                        .prepareIndex()
                        .setIndex("123")
                        .setType("user")
                         .setId("AWNnyP0pAbqHoCcSqES_") // 如果没有设置id，则ES会自动生成一个id
                        .setSource(builder.string())
                        .get();
                System.out.println(indexResponse.isCreated());
            } catch (ElasticsearchException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
    }

    @DeleteMapping(value = "{id}")
    public User delete(@PathVariable("id") String id){
        DeleteResponse deleteResponse  = this.client
                .prepareDelete()
                .setIndex("123")
                .setType("user")
                .setId(id)
                .get();
        return null;
    }

    @GetMapping(value = "{id}")
    public String get(@PathVariable("id") String id){
        GetResponse getResponse = this.client
                .prepareGet()   // 准备进行get操作，此时还有真正地执行get操作。（与直接get的区别）
                .setIndex("123")  // 要查询的
                .setType("user")
                .setId(id)
                .get();
        return getResponse.getSourceAsString();
    }

    @PutMapping
    public User put(@RequestBody User user){
        try {
            XContentBuilder builder =
                    XContentFactory.jsonBuilder()
                            .startObject()
                            .field("id", "AWNnyP0pAbqHoCcSqES_")
                            .field("name",user.getName())
                            .field("age",user.getAge())
                            .endObject();

            UpdateResponse updateResponse =
                    this.client
                            .prepareUpdate()
                            .setIndex("123")
                            .setType("user")
                            .setId("AWNnyP0pAbqHoCcSqES_")
                            .setDoc(builder.string())
                            .get();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
