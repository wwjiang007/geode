/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.geode.redis.internal.executor.key;

import static org.apache.geode.redis.internal.RedisConstants.ERROR_NO_SUCH_KEY;

import java.util.List;

import org.apache.geode.redis.internal.data.RedisKey;
import org.apache.geode.redis.internal.data.RedisKeyExistsException;
import org.apache.geode.redis.internal.executor.AbstractExecutor;
import org.apache.geode.redis.internal.executor.RedisResponse;
import org.apache.geode.redis.internal.netty.Command;
import org.apache.geode.redis.internal.netty.ExecutionHandlerContext;

public abstract class AbstractRenameExecutor extends AbstractExecutor {

  @Override
  public RedisResponse executeCommand(Command command,
      ExecutionHandlerContext context) {
    List<RedisKey> commandElems = command.getProcessedCommandKeys();
    RedisKey key = command.getKey();
    RedisKey newKey = commandElems.get(2);
    RedisKeyCommands redisKeyCommands = context.getKeyCommands();

    if (key.equals(newKey)) {
      return getTargetSameAsSourceResponse();
    }

    if (key.getSlot() != newKey.getSlot()) {
      // Will produce MOVED exceptions here for whichever key is at fault
      context.getRegionProvider().getRedisData(newKey);
      context.getRegionProvider().getRedisData(key);
    }

    try {
      if (!executeRenameCommand(key, newKey, redisKeyCommands)) {
        return getNoSuchKeyResponse();
      }
    } catch (RedisKeyExistsException ignored) {
      return getKeyExistsResponse();
    }

    return getSuccessResponse();
  }

  protected RedisResponse getNoSuchKeyResponse() {
    return RedisResponse.error(ERROR_NO_SUCH_KEY);
  }

  protected abstract boolean executeRenameCommand(RedisKey key,
      RedisKey newKey,
      RedisKeyCommands redisKeyCommands);

  protected abstract RedisResponse getTargetSameAsSourceResponse();

  protected abstract RedisResponse getSuccessResponse();

  protected abstract RedisResponse getKeyExistsResponse();

}