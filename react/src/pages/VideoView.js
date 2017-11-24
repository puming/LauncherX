/**
 * Created by pm on 17-7-28.
 */
import React, {Component} from "react";
import {Dimensions, Text, View, StyleSheet} from "react-native";
import Video from 'react-native-video';
import {NavigationActions} from 'react-navigation'

const {width: screenWidth, height: screenHeight} = Dimensions.get('window');
const element_width = screenWidth * (90 / 100);
const recommend_height = screenHeight * (40 / 100);
const list_height = screenHeight * (30 / 100);
const classsification_height = screenHeight * (10 / 100);
const TAG = "VideoView:";
export default class VideoView extends Component {
  static navigationOptions = {
    header: null,
  };

  componentWillMount() {
    console.log(TAG + "componentWillMount");
  }

  componentDidMount() {
    console.log(TAG + "componentDidMount");
  }

  componentWillReceiveProps() {
    console.log(TAG + "componentWillReceiveProps");
  }

  shouldComponentUpdate() {
    console.log(TAG + "shouldComponentUpdate");
    return false;
  }

  componentWillUpdate() {
    console.log(TAG + "componentWillUpdate");
  }

  componentDidUpdate() {
    console.log(TAG + "componentDidUpdate");
    this.completeRender = true;
  }

  componentWillUnmount() {
    console.log(TAG + "componentWillUnmount");
  }

  render() {
    return (
      <View style={styles.content}>
        <Video
          style={styles.video}
          source={{uri: this.props.navigation.state.params.uri}}
          rate={1.0}                   // 控制暂停/播放，0 代表暂停paused, 1代表播放normal.
          volume={1.0}                 // 声音的放大倍数，0 代表没有声音，就是静音muted, 1 代表正常音量 normal，更大的数字表示放大的倍数
          muted={false}                // true代表静音，默认为false.
          paused={false}               // true代表暂停，默认为false
          // resizeMode="cover"           // 视频的自适应伸缩铺放行为，
          repeat={true}                // 是否重复播放
          playInBackground={true}      // 当app转到后台运行的时候，播放是否暂停
          // playWhenInactive={false}     // [iOS] Video continues to play when control or notification center are shown. 仅适用于IOS
          onLoadStart={this._loadStart} // 当视频开始加载时的回调函数
          onLoad={this._setDuration}    // 当视频加载完毕时的回调函数
          onProgress={this.setTime}    //  进度控制，每250ms调用一次，以获取视频播放的进度
          onEnd={this._onEnd}           // 当视频播放完毕后的回调函数
          onError={this._onError}       // 当视频不能加载，或出错后的回调函数
        />
        <View style={styles.arrows}>
          <Text style={{color: "#ffffff", fontSize: 26}}
                onPress={() => {
                  this.props.navigation.goBack();
                  /*const navigationAction = NavigationActions.navigate({
                   routeName: 'Tabs',
                   params: {},

                   // navigate can have a nested navigate action that will be run inside the child router
                   action: NavigationActions.navigate({routeName: 'Tabs'})
                   });
                   this.props.navigation.dispatch(navigationAction);*/
                }}>
            返回
          </Text>
        </View>
      </View>
    );
  }

  _loadStart = () => {

  };

  _setDuration = () => {
  };

}

const styles = StyleSheet.create({
  content: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },

  arrows: {
    justifyContent:"center",
    alignItems:"center",
    position: 'absolute',
    top: 20,
    left: 20,
  },
  video: {
    position: 'absolute',
    top: 0,
    left: 0,
    bottom: 0,
    right: 0,
  },
});