/*
    Boreas
    Copyright 2016 Jie Kang, Anirudh Mukundan
    This file is part of Boreas.

    Boreas is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Boreas is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Boreas.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.boreas.web.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class GetHandler extends AbstractHandler {
    private final MongoDatabase db;

    public GetHandler(MongoDatabase db) {
        this.db = db;
    }

    @Override
    public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String collection = request.getRequestURI().substring(1);
        MongoCursor<Document> iterator = db.getCollection(collection).find().sort(new BasicDBObject("_id",-1)).limit(1).iterator();

        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.println(iterator.next().toJson());
        iterator.close();
        baseRequest.setHandled(true);
    }
}
