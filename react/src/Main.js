/**
 * Created by pm on 17-9-26.
 */
import React, {Component} from "react";
import Router from "./Router";
import {addNavigationHelpers} from "react-navigation";


export default class Main extends Component {
  render() {
    return (
      <Router
        screenProps={{width:100,height:100}}
        navigation={addNavigationHelpers({
          dispatch: this.props.dispatch,
          state: this.props.nav
        })}
      />
    );
  }
}
