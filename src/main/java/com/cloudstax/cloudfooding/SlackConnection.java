/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudstax.cloudfooding;

import com.slack.api.Slack;

/**
 *
 * @author gabriel.aguiar@VALEMOBI.CORP
 */
public class SlackConnection {
    Slack slackInstance = Slack.getInstance();
    
    public void getSlackInstance(){
        System.out.println(slackInstance);
    }
}
