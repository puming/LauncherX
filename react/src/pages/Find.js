/**
 * Created by pm on 17-7-17.
 */
import React, {Component} from 'react';
import {
  Text,
  TouchableHighlight,
  View,
  StyleSheet
} from 'react-native';

export default class Find extends Component {
  componentWillMount() {
    fetch('http://ip.taobao.com/service/getIpInfo.php?ip=59.108.51.32', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then((response) => {//1
      console.log(response);
    }).catch((err) => {//2
      console.error(err);
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableHighlight
          underlayColor='rgb(210,260,260)'
          style={{padding: 10, marginTop: 10, borderRadius: 5,}}
          onPress={this.getData}>
          <Text >get请求</Text>
        </TouchableHighlight>
      </View>
    );
  }

  getData = () => {
    console.log("getData");
    return fetch('http://ip.taobao.com/service/getIpInfo.php?ip=59.108.51.32', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then((response) => {
        console.log("result=" + `${response}`);//1
      }).catch((err) => {//2
        console.error(err);
      });
  }
}

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
  }
});