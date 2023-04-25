package etu1825.framework.servlet;

import java.util.HashMap;

import javax.servlet.http.HttpServlet;

import etu1825.framework.Mapping;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls = new HashMap<String, Mapping>();
    
}