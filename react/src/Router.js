import React, {Component} from 'react';
import {StackNavigator, TabNavigator,DrawerNavigator} from 'react-navigation';
import Ionicons from 'react-native-vector-icons/Ionicons'; // 4.4.2
import CardStackStyleInterpolator from 'react-navigation/src/views/CardStack/CardStackStyleInterpolator';
import Home from './pages/Home'
import Find from './pages/Find'
import Message from './pages/Message'
import Me from './pages/Me'
import Detail from './pages/Detail'
import VideoView from './pages/VideoView'
import {
  StyleSheet,
  Image,
  View,
  Text
} from 'react-native';

/**
 * 界面组件
 */
const TabRouteConfigMap = {
  Home: {
    screen: Home,
    navigationOptions: {
      tabBarLabel: '首页',
      tabBarIcon: ({tintColor}) =>
        <Image
          style={[styles.tabBarImage, /*{tintColor: tintColor}*/]}
          source={require('../images/mode.png')}
        />,
    }
  },

  Find: {
    screen: Find,
    navigationOptions: {
      tabBarLabel: '发现',
      tabBarIcon: ({tintColor}) =>
        <Image
          style={[styles.tabBarImage,]}
          source={require('../images/mode.png')}
        />,
    }
  },

  Message: {
    screen: Message,
    navigationOptions: {
      tabBarLabel: '消息',
      tabBarIcon: ({tintColor}) =>
        <Image
          style={[styles.tabBarImage,]}
          source={require('../images/mode.png')}
        />,
    }

  },

  Me: {
    screen: Me,
    navigationOptions: {
      title: "hello",
      tabBarVisible: true,
      tabBarLabel: '我的',
      tabBarIcon: ({tintColor}) =>
        <Image
          style={[styles.tabBarImage,]}
          source={require('../images/mode.png')}
        />,
    }
  }
};
const TabNavigatorConfig = {
  initialRouteName: 'Home',
  animationEnabled: true,
  tabBarPosition: 'bottom',
  swipeEnabled: true,
  backBehavior: 'none',
  tabBarOptions: {
    activeTintColor: 'orange',
    inactiveTintColor: 'gray',
    inactiveBackgroundColor: '#9eff5b',
    activeBackgroundColor: '#ffaeb5',
    showIcon: true,
    showLabel: true,
    upperCaseLabel: false,
    style: {
      backgroundColor: 'white',
    },
    tabStyle: {
      height: 56,
    },
    labelStyle: {
      fontSize: 12,
      marginBottom: 8,
      marginTop: 6,
    },
    iconStyle: {
      height: 32,
    },
    indicatorStyle: {
      height: 0,
    },
    pressColor: 'gray',
    pressOpacity: 0.8,
  },

};
const Tabs = TabNavigator(TabRouteConfigMap, TabNavigatorConfig);



const HomeScreen = () => (
  <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
    <Text>Home Screen</Text>
  </View>
);
const ProfileScreen = () => (
  <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
    <Text>Profile Screen</Text>
  </View>
);
//抽屉
const Drawer = DrawerNavigator(
  //NavigationRouteConfigMap
  {
    Main: {
      // screen: Tabs,
      screen: HomeScreen,
      navigationOptions: {
        drawerLabel: 'Main',
        drawerIcon: ({tintColor, focused}) => (
          <Ionicons
            name={focused ? 'ios-home' : 'ios-home-outline'}
            size={26}
            style={{color: tintColor}}
          />
        ),
      },
    },
    Profile: {
      screen: ProfileScreen,
      navigationOptions: {
        drawerLabel: 'Profile',
        drawerIcon: ({tintColor, focused}) => (
          <Ionicons
            name={focused ? 'ios-person' : 'ios-person-outline'}
            size={26}
            style={{color: tintColor}}
          />
        ),
      },
    },
  },
  //DrawerNavigatorConfig
  {
    initialRouteName: "Main",
    drawerWidth: 200,
    contentOptions: {
      activeTintColor: "#7dd2ff",
      activeBackgroundColor: "#ffc31b",
      style: {backgroundColor: "#6d1025"}
    }
  });


/**
 * 路由导航
 */
const NavigationRouteConfigMap = {
  Tabs: {
    screen: Tabs,
    // screen: Drawer,
    path: '',
    navigationOptions: {
      title: "导航",
      header: null,
      cardStack: {
        gesturesEnabled: false,
      }
    }
  },
  Detail: {
    screen: Detail,
  },
  VideoView: {screen: VideoView},
};
const StackNavigatorConfig = {
  navigationOptions: {
    headerTitle: "全局配置",
    headerBackTitle: null,
    headerTintColor: '',
    showIcon: true
  },
  modal: 'card',
  headerMode: 'screen',
  transitionConfig: () => ({
    screenInterpolator: CardStackStyleInterpolator.forHorizontal,
  })
};
const AppNavigator = StackNavigator(NavigationRouteConfigMap, StackNavigatorConfig);

const styles = StyleSheet.create({
  tabBarImage: {
    width: 24,
    height: 24,
    marginTop: 8,
  },
  tabBar: {
    backgroundColor: 'white',
  },
  tabBarLabel: {
    fontSize: 12,
    marginBottom: 8,
    marginTop: 6,
  },
  tabBarItem: {
    height: 56,
  },
  tabBarIcon: {
    height: 32,
  },
});

export default AppNavigator;